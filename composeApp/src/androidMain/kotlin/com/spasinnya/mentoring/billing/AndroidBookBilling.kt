package com.spasinnya.mentoring.billing

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*
import com.spasinnya.mentoring.domain.model.BookMeta
import com.spasinnya.mentoring.domain.model.PurchaseStatus
import com.spasinnya.mentoring.domain.repository.BookBilling
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.isSuccess
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.Serializable
import java.lang.ref.WeakReference
import kotlin.coroutines.resume

/** Non-consumable books. Google retains tokens until the backend acknowledges them. */
class AndroidBookBilling(context: Context, private val http: HttpClient) : BookBilling {
    private var activity = WeakReference<Activity>(null)
    private val connectionMutex = Mutex()
    private val purchaseMutex = Mutex()
    @Volatile private var checkout: CompletableDeferred<Pair<BillingResult, List<Purchase>>>? = null
    private val client = BillingClient.newBuilder(context.applicationContext)
        .enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build())
        .enableAutoServiceReconnection()
        .setListener { result, purchases -> checkout?.complete(result to purchases.orEmpty()) }
        .build()

    fun attach(activity: Activity) { this.activity = WeakReference(activity) }
    fun detach(activity: Activity) { if (this.activity.get() === activity) this.activity.clear() }

    private suspend fun connect() = connectionMutex.withLock {
        if (client.isReady) return@withLock
        billingTimeout(20_000) {
            suspendCancellableCoroutine { continuation ->
                client.startConnection(object : BillingClientStateListener {
                    override fun onBillingSetupFinished(result: BillingResult) {
                        if (continuation.isActive) continuation.resume(result)
                    }
                    override fun onBillingServiceDisconnected() = Unit
                })
            }.requireSuccess()
        }
    }

    private suspend inline fun <reified T> get(path: String): T {
        val response = http.get(path)
        check(response.status.isSuccess()) { "Purchase service unavailable (${response.status.value})" }
        return response.body()
    }

    private suspend fun submit(purchase: Purchase): StoreAccess {
        check(purchase.purchaseState == Purchase.PurchaseState.PURCHASED)
        val response = http.post("purchases/android") { setBody(PurchaseProof(purchase.purchaseToken)) }
        check(response.status.isSuccess()) { "Purchase verification failed (${response.status.value})" }
        return response.body<StoreAccess>().also { check(it.granted) }
    }

    private suspend fun owned(): List<Purchase> = billingTimeout(20_000) {
        suspendCancellableCoroutine { continuation ->
            client.queryPurchasesAsync(QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.INAPP).build()) { result, purchases ->
                if (continuation.isActive) continuation.resume(result to purchases)
            }
        }.let { (result, purchases) -> result.requireSuccess(); purchases }
    }

    private suspend fun details(ids: List<String>): List<ProductDetails> {
        if (ids.isEmpty()) return emptyList()
        return billingTimeout(20_000) {
            suspendCancellableCoroutine { continuation ->
                client.queryProductDetailsAsync(QueryProductDetailsParams.newBuilder()
                    .setProductList(ids.distinct().map { id -> QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(id).setProductType(BillingClient.ProductType.INAPP).build() }).build()) { result, products ->
                    if (continuation.isActive) continuation.resume(result to products.productDetailsList)
                }
            }.let { (result, products) -> result.requireSuccess(); products }
        }
    }

    override suspend fun refresh(books: List<BookMeta>): List<BookMeta> = try {
        val storeAccount = get<StoreAccount>("purchases/android/account")
        if (storeAccount.billingEnabled) refreshWithBilling(books, storeAccount.obfuscatedAccountId)
        else books.map { it.withFreeAccess() }
    } catch (e: CancellationException) {
        throw e
    } catch (_: Exception) {
        books.map { it.withFreeAccess() }
    }

    private suspend fun refreshWithBilling(books: List<BookMeta>, account: String): List<BookMeta> {
        connect()
        // Fetch per language explicitly: backend IDs belong to a localized book.
        val catalog = books.map { it.language }.distinct().associateWith { language ->
            val response = http.get("books") { header("Accept-Language", language) }
            check(response.status.isSuccess())
            response.body<List<ServerBook>>()
        }
        if (checkout == null) {
            for (purchase in owned()) {
                if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED &&
                    purchase.accountIdentifiers?.obfuscatedAccountId == account) {
                    // Retry all completed purchases, including already acknowledged purchases.
                    try { submit(purchase) } catch (e: CancellationException) { throw e }
                    catch (_: Exception) { /* Retry on next foreground/catalog refresh. */ }
                }
            }
        }
        val products = details(books.map { it.productId })
        // Read after restore so newly granted access appears immediately.
        val access = get<Entitlements>("me/entitlements").products.toSet()
        return books.map { book ->
            val serverBook = catalog[book.language]?.singleOrNull { it.number == book.bookNumber.toString() }
            val offer = products.singleOrNull { it.productId == book.productId }?.permanentOffer()
            if (serverBook == null || offer == null) book.withFreeAccess()
            else book.copy(
                isPurchased = serverBook.id in access,
                priceLabel = offer.formattedPrice,
                canPurchase = true,
            )
        }
    }

    private fun BookMeta.withFreeAccess() = copy(isPurchased = true, priceLabel = "", canPurchase = false)

    override suspend fun purchase(book: BookMeta): PurchaseStatus = purchaseMutex.withLock {
        val storeAccount = get<StoreAccount>("purchases/android/account")
        check(storeAccount.billingEnabled) { "Android billing is disabled" }
        connect()
        val account = storeAccount.obfuscatedAccountId
        val previous = owned().firstOrNull { book.productId in it.products }
        if (previous != null) {
            if (previous.purchaseState == Purchase.PurchaseState.PENDING)
                return@withLock PurchaseStatus(0, false, pending = true)
            check(previous.accountIdentifiers?.obfuscatedAccountId == account) { "Purchase belongs to another account" }
            val access = submit(previous)
            return@withLock PurchaseStatus(access.productId.toInt(), access.granted)
        }
        val product = details(listOf(book.productId)).singleOrNull() ?: error("Product unavailable")
        val offer = product.permanentOffer() ?: error("Purchase offer unavailable")
        val deferred = CompletableDeferred<Pair<BillingResult, List<Purchase>>>()
        checkout = deferred
        try {
            val launchResult = withContext(Dispatchers.Main.immediate) {
                val host = activity.get()?.takeUnless { it.isFinishing || it.isDestroyed }
                    ?: error("No active purchase screen")
                val productBuilder = BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(product)
                offer.offerToken?.let(productBuilder::setOfferToken)
                val productParams = productBuilder.build()
                client.launchBillingFlow(host, BillingFlowParams.newBuilder()
                    .setObfuscatedAccountId(account).setProductDetailsParamsList(listOf(productParams)).build())
            }
            if (launchResult.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
                val purchase = owned().first { book.productId in it.products }
                if (purchase.purchaseState == Purchase.PurchaseState.PENDING)
                    return@withLock PurchaseStatus(0, false, pending = true)
                val access = submit(purchase)
                return@withLock PurchaseStatus(access.productId.toInt(), access.granted)
            }
            launchResult.requireSuccess()
            val (result, purchases) = billingTimeout(5 * 60_000L) { deferred.await() }
            if (result.responseCode == BillingClient.BillingResponseCode.USER_CANCELED)
                return@withLock PurchaseStatus(0, false)
            result.requireSuccess()
            val purchase = purchases.firstOrNull { book.productId in it.products } ?: error("Purchase missing")
            if (purchase.purchaseState == Purchase.PurchaseState.PENDING)
                return@withLock PurchaseStatus(0, false, pending = true)
            // Server checks the token, account, product mapping and Google state before granting access.
            val access = submit(purchase)
            PurchaseStatus(access.productId.toInt(), access.granted)
        } finally {
            checkout = null
        }
    }

    private fun ProductDetails.permanentOffer(): ProductDetails.OneTimePurchaseOfferDetails? =
        (oneTimePurchaseOfferDetailsList.orEmpty().ifEmpty { listOfNotNull(oneTimePurchaseOfferDetails) })
            .filter { it.rentalDetails == null && it.preorderDetails == null }
            .minByOrNull { it.priceAmountMicros }

    private suspend fun <T> billingTimeout(timeout: Long, block: suspend CoroutineScope.() -> T): T {
        // Only a store timeout becomes an error; cancellation of the screen still propagates.
        return withTimeoutOrNull(timeout) { Result.success(block()) }?.getOrThrow()
            ?: error("Google Play timed out")
    }

    private fun BillingResult.requireSuccess() {
        check(responseCode == BillingClient.BillingResponseCode.OK) { "Google Play billing error ($responseCode)" }
    }
}

@Serializable private data class StoreAccount(val obfuscatedAccountId: String, val billingEnabled: Boolean = true)
@Serializable private data class PurchaseProof(val purchaseToken: String)
@Serializable private data class StoreAccess(val productId: Long, val granted: Boolean)
@Serializable private data class Entitlements(val products: List<Long>)
@Serializable private data class ServerBook(val id: Long, val number: String)

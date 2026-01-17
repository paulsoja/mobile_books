package com.spasinnya.mentoring.data.storage.datastore

import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

actual fun provideLocaleStore(appContext: Any): LocaleStore {
    val documents = (NSSearchPathForDirectoriesInDomains(
        NSDocumentDirectory, NSUserDomainMask, true
    ).first() as String).toPath()
    val file = (documents / "datastore" / "locale.preferences")
    return localeStoreFromPath(file)
}
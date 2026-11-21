package com.spasinnya.mentoring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.spasinnya.mentoring.di.initKoin
import com.spasinnya.mentoring.presentation.app.AppContent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.android.get
import org.koin.core.context.GlobalContext
import com.spasinnya.mentoring.billing.AndroidBookBilling

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        if (GlobalContext.getOrNull() == null) initKoin {
            androidContext(applicationContext)
        }

        get<AndroidBookBilling>().attach(this)

        val insets = WindowCompat.getInsetsController(window, window.decorView.rootView)
        insets.isAppearanceLightStatusBars = true
        insets.isAppearanceLightNavigationBars = true

        setContent {
            AppContent()
        }
    }
    override fun onResume() {
        super.onResume()
        get<AndroidBookBilling>().attach(this)
    }

    override fun onDestroy() {
        get<AndroidBookBilling>().detach(this)
        super.onDestroy()
    }
}
package com.spasinnya.mentoring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.spasinnya.mentoring.di.initKoin
import com.spasinnya.mentoring.presentation.app.AppContent
import com.spasinnya.mentoring.presentation.designsystem.BooksTheme
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        initKoin {
            androidContext(this@MainActivity)
        }

        val insets = WindowCompat.getInsetsController(window, window.decorView.rootView)
        insets.isAppearanceLightStatusBars = true
        insets.isAppearanceLightNavigationBars = true

        setContent {
            BooksTheme(darkTheme = false) {
                AppContent()
            }
        }
    }
}
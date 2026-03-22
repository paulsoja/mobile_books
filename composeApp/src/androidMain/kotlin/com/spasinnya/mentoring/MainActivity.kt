package com.spasinnya.mentoring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.spasinnya.mentoring.presentation.app.AppContent
import com.spasinnya.mentoring.presentation.designsystem.BooksTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)


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
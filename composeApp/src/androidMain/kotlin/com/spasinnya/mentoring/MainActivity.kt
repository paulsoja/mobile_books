package com.spasinnya.mentoring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.graphics.toColorInt
import androidx.core.view.WindowCompat
import com.spasinnya.mentoring.presentation.AppContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto("#F5F7FC".toColorInt(), "#F5F7FC".toColorInt()),
            navigationBarStyle = SystemBarStyle.auto("#F5F7FC".toColorInt(), "#F5F7FC".toColorInt())
        )

        val insets = WindowCompat.getInsetsController(window, window.decorView.rootView)
        insets.isAppearanceLightStatusBars = true
        insets.isAppearanceLightNavigationBars = true

        setContent {
            AppContent()
        }
    }
}
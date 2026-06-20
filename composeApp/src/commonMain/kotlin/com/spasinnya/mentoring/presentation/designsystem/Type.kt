package com.spasinnya.mentoring.presentation.designsystem

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.raleway_black
import com.spasinnya.mentoring.generated.resources.raleway_bold
import com.spasinnya.mentoring.generated.resources.raleway_extra_bold
import com.spasinnya.mentoring.generated.resources.raleway_extra_light
import com.spasinnya.mentoring.generated.resources.raleway_light
import com.spasinnya.mentoring.generated.resources.raleway_medium
import com.spasinnya.mentoring.generated.resources.raleway_regular
import com.spasinnya.mentoring.generated.resources.raleway_semi_bold
import com.spasinnya.mentoring.generated.resources.raleway_thin
import org.jetbrains.compose.resources.Font

@Composable
fun ralewayFamily() = FontFamily(
    Font(Res.font.raleway_semi_bold, weight = FontWeight.SemiBold),
    Font(Res.font.raleway_medium, weight = FontWeight.Medium),
    Font(Res.font.raleway_regular, weight = FontWeight.Normal),
    Font(Res.font.raleway_bold, weight = FontWeight.Bold),
    Font(Res.font.raleway_black, weight = FontWeight.Black),
    Font(Res.font.raleway_thin, weight = FontWeight.Thin),
    Font(Res.font.raleway_light, weight = FontWeight.Light),
    Font(Res.font.raleway_extra_light, weight = FontWeight.ExtraLight),
    Font(Res.font.raleway_extra_bold, weight = FontWeight.ExtraBold),
)

@Composable
fun appTypography() = Typography().run {
    val fontFamily = ralewayFamily()
    copy(
        displayLarge  = this.displayLarge.copy(fontFamily = fontFamily),
        displayMedium = this.displayMedium.copy(fontFamily = fontFamily),
        displaySmall  = this.displaySmall.copy(fontFamily = fontFamily),

        headlineLarge  = this.headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = this.headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall  = this.headlineSmall.copy(fontFamily = fontFamily),

        titleLarge  = this.titleLarge.copy(fontFamily = fontFamily),
        titleMedium = this.titleMedium.copy(fontFamily = fontFamily),
        titleSmall  = this.titleSmall.copy(fontFamily = fontFamily),

        bodyLarge  = this.bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = this.bodyMedium.copy(fontFamily = fontFamily),
        bodySmall  = this.bodySmall.copy(fontFamily = fontFamily),

        labelLarge  = this.labelLarge.copy(fontFamily = fontFamily),
        labelMedium = this.labelMedium.copy(fontFamily = fontFamily),
        labelSmall  = this.labelSmall.copy(fontFamily = fontFamily),
    )
}
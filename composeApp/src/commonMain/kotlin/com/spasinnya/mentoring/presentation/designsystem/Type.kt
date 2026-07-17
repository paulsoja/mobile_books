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
fun appTypography(): Typography {
    val family = ralewayFamily()

    return Typography(
        bodySmall = Typography().bodySmall.copy(fontFamily = family),
        bodyMedium = Typography().bodyMedium.copy(fontFamily = family),
        bodyLarge = Typography().bodyLarge.copy(fontFamily = family),

        titleSmall = Typography().titleSmall.copy(fontFamily = family),
        titleMedium = Typography().titleMedium.copy(fontFamily = family),
        titleLarge = Typography().titleLarge.copy(fontFamily = family),

        headlineSmall = Typography().headlineSmall.copy(fontFamily = family),
        headlineMedium = Typography().headlineMedium.copy(fontFamily = family),
        headlineLarge = Typography().headlineLarge.copy(fontFamily = family),

        displaySmall = Typography().displaySmall.copy(fontFamily = family),
        displayMedium = Typography().displayMedium.copy(fontFamily = family),
        displayLarge = Typography().displayLarge.copy(fontFamily = family),

        labelSmall = Typography().labelSmall.copy(fontFamily = family),
        labelMedium = Typography().labelMedium.copy(fontFamily = family),
        labelLarge = Typography().labelLarge.copy(fontFamily = family),
    )
}
package com.spasinnya.mentoring.presentation.designsystem

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.raleway_black
import books.composeapp.generated.resources.raleway_bold
import books.composeapp.generated.resources.raleway_extra_bold
import books.composeapp.generated.resources.raleway_extra_light
import books.composeapp.generated.resources.raleway_light
import books.composeapp.generated.resources.raleway_medium
import books.composeapp.generated.resources.raleway_regular
import books.composeapp.generated.resources.raleway_semi_bold
import books.composeapp.generated.resources.raleway_thin
import org.jetbrains.compose.resources.Font

@Composable
fun RalewayFamily() = FontFamily(
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
fun AppTypography() = Typography().run {
    val fontFamily = RalewayFamily()
    copy(
        h1 = this.h1.copy(fontFamily = fontFamily),
        h2 = this.h2.copy(fontFamily = fontFamily),
        h3 = this.h3.copy(fontFamily = fontFamily),
        h4 = this.h4.copy(fontFamily = fontFamily),
        h5 = this.h5.copy(fontFamily = fontFamily),
        h6 = this.h6.copy(fontFamily = fontFamily),
        subtitle1 = this.subtitle1.copy(fontFamily = fontFamily),
        subtitle2 = this.subtitle2.copy(fontFamily = fontFamily),
        body1 = this.body1.copy(fontFamily = fontFamily),
        body2 = this.body2.copy(fontFamily = fontFamily),
        button = this.button.copy(fontFamily = fontFamily),
        caption = this.caption.copy(fontFamily = fontFamily),
        overline = this.overline.copy(fontFamily = fontFamily),
    )
}
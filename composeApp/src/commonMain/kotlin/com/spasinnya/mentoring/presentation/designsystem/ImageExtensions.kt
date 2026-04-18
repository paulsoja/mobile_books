package com.spasinnya.mentoring.presentation.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.cover_de_1
import books.composeapp.generated.resources.cover_en_1
import books.composeapp.generated.resources.cover_ru_0
import books.composeapp.generated.resources.cover_ua_0
import books.composeapp.generated.resources.cover_ua_1
import books.composeapp.generated.resources.cover_ua_2
import books.composeapp.generated.resources.cover_ua_3
import books.composeapp.generated.resources.cover_ua_4
import com.spasinnya.mentoring.domain.enums.Language
import com.spasinnya.mentoring.presentation.base.LocalAppLocale
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import kotlin.to

private fun coverResOrNull(lang: Language, bookNumber: Int): DrawableResource? =
    coverMap[lang to bookNumber]

private val coverMap: Map<Pair<Language, Int>, DrawableResource> = mapOf(
    (Language.UA to 0) to Res.drawable.cover_ua_0,
    (Language.UA to 1) to Res.drawable.cover_ua_1,
    (Language.UA to 2) to Res.drawable.cover_ua_2,
    (Language.UA to 3) to Res.drawable.cover_ua_3,
    (Language.UA to 4) to Res.drawable.cover_ua_4,

    (Language.RU to 0) to Res.drawable.cover_ru_0,
    (Language.RU to 1) to Res.drawable.cover_ua_1,
    (Language.RU to 2) to Res.drawable.cover_ua_2,
    (Language.RU to 3) to Res.drawable.cover_ua_3,
    (Language.RU to 4) to Res.drawable.cover_ua_4,

    (Language.EN to 0) to Res.drawable.cover_ua_0,
    (Language.EN to 1) to Res.drawable.cover_en_1,
    (Language.EN to 2) to Res.drawable.cover_ua_2,
    (Language.EN to 3) to Res.drawable.cover_ua_3,
    (Language.EN to 4) to Res.drawable.cover_ua_4,

    (Language.DE to 0) to Res.drawable.cover_ua_0,
    (Language.DE to 1) to Res.drawable.cover_de_1,
    (Language.DE to 2) to Res.drawable.cover_ua_2,
    (Language.DE to 3) to Res.drawable.cover_ua_3,
    (Language.DE to 4) to Res.drawable.cover_ua_4,
)

@Composable
fun coverPainter(
    bookNumber: Int,
    fallbackColor: Color = Color(0xFFE6E6E6),
): Painter {
    val langTag = LocalAppLocale.current
    val lang = Language.fromTag(langTag)

    val res = coverResOrNull(lang, bookNumber)
    return if (res != null) painterResource(res) else ColorPainter(fallbackColor)
}
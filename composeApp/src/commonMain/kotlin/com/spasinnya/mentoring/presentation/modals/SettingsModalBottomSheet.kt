package com.spasinnya.mentoring.presentation.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_chevron_right
import books.composeapp.generated.resources.ic_gift_2
import books.composeapp.generated.resources.ic_lang_en
import books.composeapp.generated.resources.ic_lang_ru
import books.composeapp.generated.resources.ic_lang_ua
import books.composeapp.generated.resources.ic_logout_4
import books.composeapp.generated.resources.ic_passport
import com.spasinnya.mentoring.domain.enums.Language
import com.spasinnya.mentoring.presentation.designsystem.BooksTheme
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreCardWithContent
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreHorizontalDividerLight
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreModalBottomSheet
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreModalTopBar
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVertical
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalLarge
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalMedium
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalSmall
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextSubtitle
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SettingsModalBottomSheet(
    onClose: () -> Unit,
    onLanguageChosen: (lang: Language) -> Unit,
    onProfileClick: () -> Unit,
    onPromoCodesClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onAuthorsClick: () -> Unit,
    onSpasinnyaBooksClick: () -> Unit,
    onSpasinnyaChurchClick: () -> Unit,
    selectedLanguage: Language,
    ) {
    CoreModalBottomSheet(
        onDismissRequest = onClose,
    ) {
        SettingsModalBottomSheetContent(
            onLanguageChosen = onLanguageChosen,
            onClose = onClose,
            onProfileClick = onProfileClick,
            onPromoCodesClick = onPromoCodesClick,
            onLogoutClick = onLogoutClick,
            onAuthorsClick = onAuthorsClick,
            onSpasinnyaBooksClick = onSpasinnyaBooksClick,
            onSpasinnyaChurchClick = onSpasinnyaChurchClick,
            selectedLanguage = selectedLanguage
        )
    }
}

@Composable
fun SettingsModalBottomSheetContent(
    onLanguageChosen: (lang: Language) -> Unit,
    onClose: () -> Unit,
    onProfileClick: () -> Unit,
    onPromoCodesClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onAuthorsClick: () -> Unit,
    onSpasinnyaBooksClick: () -> Unit,
    onSpasinnyaChurchClick: () -> Unit,
    selectedLanguage: Language,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoreModalTopBar(onCloseClick = onClose)

        CoreSpacerVerticalLarge()

        CoreTextBody(text = "Мова додатку:")

        CoreSpacerVerticalMedium()

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            LangOptionUi(
                iconRes = Res.drawable.ic_lang_ua,
                text = "Укр",
                isSelected = selectedLanguage == Language.UA,
                onClick = {
                    onLanguageChosen(Language.UA)
                })
            LangOptionUi(
                iconRes = Res.drawable.ic_lang_en,
                text = "Eng",
                isSelected = selectedLanguage == Language.EN,
                onClick = {
                    onLanguageChosen(Language.EN)
                })
            LangOptionUi(
                iconRes = Res.drawable.ic_lang_ru,
                text = "Рус",
                isSelected = selectedLanguage == Language.RU,
                onClick = {
                    onLanguageChosen(Language.RU)
                })
        }

        CoreSpacerVertical(48.dp)

        CoreCardWithContent(contentPadding = 0.dp) {
            Column(modifier = Modifier.fillMaxWidth()) {
                OptionUi(
                    iconRes = Res.drawable.ic_passport,
                    title = "Мій профайл",
                    onClick = onProfileClick
                )
                CoreHorizontalDividerLight()
                OptionUi(
                    iconRes = Res.drawable.ic_gift_2,
                    title = "Промокоди",
                    onClick = onPromoCodesClick
                )
                CoreHorizontalDividerLight()
                OptionUi(
                    iconRes = Res.drawable.ic_logout_4,
                    title = "Вийти",
                    onClick = onLogoutClick
                )
            }
        }

        CoreSpacerVertical(48.dp)

        CoreCardWithContent(contentPadding = 0.dp) {
            Column(modifier = Modifier.fillMaxWidth()) {
                OptionUi(
                    title = "Автори посібників",
                    onClick = onAuthorsClick
                )
                CoreHorizontalDividerLight()
                OptionUi(
                    title = "SPASINNYA BOOKS",
                    onClick = onSpasinnyaBooksClick
                )
                CoreHorizontalDividerLight()
                OptionUi(
                    title = "Церква «Спасіння»",
                    onClick = onSpasinnyaChurchClick
                )
            }
        }

        CoreSpacerVertical(48.dp)

    }
}

@Composable
private fun LangOptionUi(
    iconRes: DrawableResource,
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick, interactionSource = remember { MutableInteractionSource() }, indication = null)
            .background(color = if (isSelected) Color.White else Color.Unspecified)
            .padding(all = 12.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(resource = iconRes),
            contentDescription = text,
            tint = Color.Unspecified
        )
        CoreSpacerVerticalSmall()
        CoreTextBody(text = text)
    }
}

@Composable
private fun OptionUi(
    iconRes: DrawableResource? = null,
    title: String,
    hasArrowRight: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.height(56.dp).clickable(onClick = onClick).padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        iconRes?.let { icon ->
            Icon(
                painter = painterResource(resource = icon),
                contentDescription = title,
                tint = Color(0xFFDFA672)
            )
        }

        CoreTextSubtitle(modifier = Modifier.weight(1f), text = title)

        if (hasArrowRight) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(resource = Res.drawable.ic_chevron_right),
                contentDescription = null,
                tint = Color(0xFFB6C3D8)
            )
        }
    }
}

@Preview
@Composable
fun SettingsModalBottomSheetPreview() {
    Surface(contentColor = Color(0xFFF5F7FC)) {
        BooksTheme {
            SettingsModalBottomSheetContent(
                onClose = {},
                onLanguageChosen = {},
                onProfileClick = {},
                onPromoCodesClick = {},
                onLogoutClick = {},
                onAuthorsClick = {},
                onSpasinnyaBooksClick = {},
                onSpasinnyaChurchClick = {},
                selectedLanguage = Language.EN
            )
        }
    }
}
package com.spasinnya.mentoring.presentation.screens.homeflow.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.auth_logout
import books.composeapp.generated.resources.home_settings_app_language
import books.composeapp.generated.resources.home_settings_autors
import books.composeapp.generated.resources.home_settings_my_profile
import books.composeapp.generated.resources.home_settings_promocodes
import books.composeapp.generated.resources.home_settings_salvation_church
import books.composeapp.generated.resources.ic_chevron_right
import books.composeapp.generated.resources.ic_gift_2
import books.composeapp.generated.resources.ic_lang_en
import books.composeapp.generated.resources.ic_lang_ru
import books.composeapp.generated.resources.ic_lang_ua
import books.composeapp.generated.resources.ic_logout_4
import books.composeapp.generated.resources.ic_passport
import com.spasinnya.mentoring.domain.enums.Language
import com.spasinnya.mentoring.presentation.base.LocalAppLocale
import com.spasinnya.mentoring.presentation.designsystem.BooksTheme
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreCardWithContent
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreHorizontalDividerLight
import com.spasinnya.mentoring.presentation.designsystem.composable.modal.CoreModalBottomSheet
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreModalTopBar
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVertical
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalLarge
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalMedium
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalSmall
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextSubtitle
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
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
) {
    CoreModalBottomSheet<SettingsSheetAction>(
        onDismissed = onClose,
        onAction = { action ->
            when (action) {
                Close -> Unit
                is LanguageChosen -> onLanguageChosen(action.lang)
                ProfileClick -> onProfileClick()
                PromoCodesClick -> onPromoCodesClick()
                LogoutClick -> onLogoutClick()
                AuthorsClick -> onAuthorsClick()
                SpasinnyaBooksClick -> onSpasinnyaBooksClick()
                SpasinnyaChurchClick -> onSpasinnyaChurchClick()
            }
        },
        shouldDismissOnAction = { it is SettingsSheetDismissAction }
    ) {
        SettingsModalBottomSheetContent(
            onAction = ::send
        )
    }
}

@Composable
fun SettingsModalBottomSheetContent(
    onAction: (SettingsSheetAction) -> Unit
) {
    val currentLanguageTag = LocalAppLocale.current

    val languageOptions: List<LangOption> = listOf(
        LangOption(
            lang = Language.UA,
            iconRes = Res.drawable.ic_lang_ua,
            label = "Укр",
        ),
        LangOption(
            lang = Language.EN,
            iconRes = Res.drawable.ic_lang_en,
            label = "Eng",
        ),
        LangOption(
            lang = Language.RU,
            iconRes = Res.drawable.ic_lang_ru,
            label = "Рус",
        ),
        LangOption(
            lang = Language.DE, // ✅
            iconRes = Res.drawable.ic_lang_en,
            label = "De",
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoreModalTopBar(onCloseClick = { onAction(Close) })

        CoreSpacerVerticalLarge()

        CoreTextBody(text = stringResource(Res.string.home_settings_app_language))

        CoreSpacerVerticalMedium()

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            languageOptions.forEach { option ->
                LangOptionUi(
                    iconRes = option.iconRes,
                    text = option.label,
                    isSelected = option.lang.tag == currentLanguageTag,
                    onClick = { onAction(LanguageChosen(option.lang)) }
                )
            }
        }

        CoreSpacerVertical(48.dp)

        CoreCardWithContent(contentPadding = 0.dp) {
            Column(modifier = Modifier.fillMaxWidth()) {
                OptionUi(
                    iconRes = Res.drawable.ic_passport,
                    title = stringResource(Res.string.home_settings_my_profile),
                    onClick = { onAction(ProfileClick) }
                )
                CoreHorizontalDividerLight()
                OptionUi(
                    iconRes = Res.drawable.ic_gift_2,
                    title = stringResource(Res.string.home_settings_promocodes),
                    onClick = { onAction(PromoCodesClick) }
                )
                CoreHorizontalDividerLight()
                OptionUi(
                    iconRes = Res.drawable.ic_logout_4,
                    title = stringResource(Res.string.auth_logout),
                    onClick = { onAction(LogoutClick) }
                )
            }
        }

        CoreSpacerVertical(48.dp)

        CoreCardWithContent(contentPadding = 0.dp) {
            Column(modifier = Modifier.fillMaxWidth()) {
                OptionUi(
                    title = stringResource(Res.string.home_settings_autors),
                    onClick = { onAction(AuthorsClick) }
                )
                CoreHorizontalDividerLight()
                OptionUi(
                    title = "SPASINNYA BOOKS",
                    onClick = { onAction(SpasinnyaBooksClick) }
                )
                CoreHorizontalDividerLight()
                OptionUi(
                    title = stringResource(Res.string.home_settings_salvation_church),
                    onClick = { onAction(SpasinnyaChurchClick) }
                )
            }
        }

        CoreSpacerVertical(48.dp)
    }
}

@Stable
private data class LangOption(
    val lang: Language,
    val iconRes: DrawableResource,
    val label: String
)

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
            .clickable(onClick = onClick)
            .background(color = if (isSelected) Color.White else Color.Unspecified)
            .padding(all = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
        modifier = Modifier
            .height(56.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp),
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
                onAction = {}
            )
        }
    }
}
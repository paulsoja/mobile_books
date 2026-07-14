package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreModalTopBar
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalLarge
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.modal.CoreModalBottomSheet

@Composable
fun PracticalWorkModalBottomSheet(
    onClose: () -> Unit,
) {
    CoreModalBottomSheet<PracticalWorkSheetAction>(
        onDismissed = onClose,
        onAction = { action ->
            when (action) {
                PracticalWorkClose -> Unit
            }
        },
        shouldDismissOnAction = { it is PracticalWorkSheetDismissAction }
    ) {
        PracticalWorkModalBottomSheetContent(
            onAction = ::send
        )
    }
}

@Composable
private fun PracticalWorkModalBottomSheetContent(
    onAction: (PracticalWorkSheetAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
    ) {
        CoreModalTopBar(
            title = "Практична робота",
            onCloseClick = { onAction(PracticalWorkClose) }
        )

        CoreSpacerVerticalLarge()

        CoreTextBody(
            modifier = Modifier.fillMaxWidth(),
            text = "",
        )
    }
}

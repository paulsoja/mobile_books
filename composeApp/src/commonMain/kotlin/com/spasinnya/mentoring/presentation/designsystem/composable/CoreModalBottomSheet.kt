package com.spasinnya.mentoring.presentation.designsystem.composable

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoreModalBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    containerColor: Color = Color(0xFFF5F7FC), // TODO move to design system
    showDragHandle: Boolean = true,
    enablePadding: Boolean = true,
    contentWindowInsets: @Composable () -> WindowInsets = { BottomSheetDefaults.windowInsets },
    content: @Composable () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        modifier = if (enablePadding) modifier.statusBarsPadding() else modifier,
        onDismissRequest = onDismissRequest,
        sheetState = modalBottomSheetState,
        dragHandle = { if (showDragHandle) DragHandlerBottomSheet() else null },
        containerColor = containerColor,
        shape = RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 12.dp
        ),
        contentWindowInsets = contentWindowInsets
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DragHandlerBottomSheet() {
    BottomSheetDefaults.DragHandle(
        width = 33.dp,
        height = 3.dp,
        shape = RoundedCornerShape(3.dp),
        color = BottomSheetDefaults.ScrimColor,
    )
}
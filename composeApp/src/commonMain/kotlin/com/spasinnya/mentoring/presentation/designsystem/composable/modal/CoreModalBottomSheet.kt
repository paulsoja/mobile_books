package com.spasinnya.mentoring.presentation.designsystem.composable.modal

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <A> CoreModalBottomSheet(
    modifier: Modifier = Modifier,
    onDismissed: () -> Unit,
    onAction: (A) -> Unit,
    shouldDismissOnAction: (A) -> Boolean = { true },

    containerColor: Color = Color(0xFFF5F7FC), // TODO move to design system
    showDragHandle: Boolean = true,
    enablePadding: Boolean = true,
    contentWindowInsets: @Composable () -> WindowInsets = { BottomSheetDefaults.windowInsets },

    content: @Composable CoreBottomSheetScope<A>.() -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // avoid stale lambdas across recompositions
    val latestOnDismissed by rememberUpdatedState(onDismissed)
    val latestOnAction by rememberUpdatedState(onAction)
    val latestShouldDismissOnAction by rememberUpdatedState(shouldDismissOnAction)

    fun dismissInternal(after: (() -> Unit)? = null) {
        coroutineScope.launch {
            if (sheetState.isVisible) {
                sheetState.hide() // ✅ close animation
            }
            after?.invoke()
            latestOnDismissed()
        }
    }

    val sheetScope = object : CoreBottomSheetScope<A> {
        override fun send(action: A) {
            if (latestShouldDismissOnAction(action)) {
                dismissInternal(after = { latestOnAction(action) })
            } else {
                latestOnAction(action)
            }
        }

        override fun dismiss() {
            dismissInternal(after = null)
        }
    }

    ModalBottomSheet(
        modifier = if (enablePadding) modifier.statusBarsPadding() else modifier,
        onDismissRequest = { sheetScope.dismiss() }, // ✅ no recursion
        sheetState = sheetState,
        dragHandle = { if (showDragHandle) DragHandlerBottomSheet() else null },
        containerColor = containerColor,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        contentWindowInsets = contentWindowInsets
    ) {
        sheetScope.content()
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
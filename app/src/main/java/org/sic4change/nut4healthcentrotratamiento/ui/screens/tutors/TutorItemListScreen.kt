package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.ui.commons.ErrorMessage
import org.sic4change.nut4healthcentrotratamiento.data.entities.Result

@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun TutorItemsListScreen(
    loading: Boolean = false,
    items: Result<List<Tutor>>,
    onClick: (Tutor) -> Unit
) {
    items.fold( { ErrorMessage(it)}) { tutors ->

        var bottomSheetItem by remember { mutableStateOf<Tutor?>(null) }
        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()

        BackHandler(sheetState.isVisible) {
            scope.launch {
                sheetState.hide()
            }
        }

        ModalBottomSheetLayout(sheetContent = {
            TutorItemBottomPreview(
                item = bottomSheetItem,
                onGoToDetail = {
                    scope.launch {
                        sheetState.hide()
                        onClick(it)
                    }
                }
            )
        }, sheetState = sheetState) {
            TutorItemsList(
                loading = loading,
                items = tutors,
                onItemClick = onClick,
                onItemMore = {
                    bottomSheetItem = it
                    scope.launch {
                        sheetState.show()
                    }
                }
            )
        }
    }
}

@Composable
fun BackPressHandler(enabled: Boolean, onBack: () -> Unit) {
    val lifecycleOwener = LocalLifecycleOwner.current
    val backDispatcher = requireNotNull(LocalOnBackPressedDispatcherOwner.current).onBackPressedDispatcher


    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                onBack()
            }
        }
    }

    SideEffect {
        backCallback.isEnabled = enabled
    }

    DisposableEffect(lifecycleOwener, backDispatcher) {
        backDispatcher.addCallback(lifecycleOwener, backCallback)
        onDispose { backCallback.remove() }
    }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun  TutorItemsList(
    loading: Boolean,
    items: List<Tutor>,
    onItemClick: (Tutor) -> Unit,
    onItemMore: (Tutor) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator()
        }
        if (items.isNotEmpty()) {
            LazyVerticalGrid(
                cells = GridCells.Adaptive(180.dp),
                contentPadding = PaddingValues(4.dp),
                modifier = modifier
            ) {
                items(items) {
                    TutorListItem(
                        item = it,
                        modifier = Modifier.clickable { onItemClick(it) },
                        onItemMore = onItemMore
                    )
                }
            }
        }
    }

}
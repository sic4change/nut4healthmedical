package org.sic4change.nut4healthcentrotratamiento.ui.screens.childs

import android.os.Build
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.RequiresApi
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
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun ChildItemsListScreen(
    loading: Boolean = false,
    items: List<Child>,
    onClick: (Child) -> Unit
) {
        var bottomSheetItem by remember { mutableStateOf<Child?>(null) }
        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()

        BackHandler(sheetState.isVisible) {
            scope.launch {
                sheetState.hide()
            }
        }

        ModalBottomSheetLayout(sheetContent = {
            ChildItemBottomPreview(
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
                items = items,
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
    items: List<Child>,
    onItemClick: (Child) -> Unit,
    onItemMore: (Child) -> Unit,
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
                cells = GridCells.Adaptive(4000.dp),
                contentPadding = PaddingValues(4.dp),
                modifier = modifier
            ) {
                items(items) {
                    ChildListItem(
                        item = it,
                        modifier = Modifier.clickable { onItemClick(it) },
                        onItemMore = onItemMore
                    )
                }
            }
        }
    }

}
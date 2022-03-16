package org.sic4change.nut4healthcentrotratamiento.ui.screens.cases

import android.os.Build
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun CaseItemsListScreen(
    loading: Boolean = false,
    items: List<Case>,
    onClick: (Case) -> Unit
) {
        var bottomSheetItem by remember { mutableStateOf<Case?>(null) }
        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()

        BackHandler(sheetState.isVisible) {
            scope.launch {
                sheetState.hide()
            }
        }

        ModalBottomSheetLayout(sheetContent = {
            CaseItemBottomPreview(
                item = bottomSheetItem,
                onGoToDetail = {
                    scope.launch {
                        sheetState.hide()
                        onClick(it)
                    }
                }
            )
        }, sheetState = sheetState) {
            CaseItemsList(
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
fun CaseItemsList(
    loading: Boolean,
    items: List<Case>,
    onItemClick: (Case) -> Unit,
    onItemMore: (Case) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.casos),
            color = colorResource(R.color.colorPrimary),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
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
                        CaseListItem(
                            item = it,
                            modifier = Modifier.clickable { onItemClick(it) },
                            onItemMore = onItemMore
                        )
                    }
                }
            } else {
                Text(
                    text = stringResource(R.string.no_cases),
                    color = colorResource(R.color.disabled_color),
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }


}
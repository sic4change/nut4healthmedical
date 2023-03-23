package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits

import android.os.Build
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun VisitItemsListScreen(
    loading: Boolean = false,
    items: List<Visit>,
    onClick: (Visit) -> Unit
) {
        var bottomSheetItem by remember { mutableStateOf<Visit?>(null) }
        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()

        BackHandler(sheetState.isVisible) {
            scope.launch {
                sheetState.hide()
            }
        }

        if (loading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = colorResource(R.color.colorPrimaryDark))
            }
        }

        ModalBottomSheetLayout(sheetContent = {
            VisitItemBottomPreview(
                item = bottomSheetItem,
                onGoToDetail = {
                    scope.launch {
                        sheetState.hide()
                        onClick(it)
                    }
                }
            )
        }, sheetState = sheetState) {
            VisitItemsList(
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
fun VisitItemsList(
    loading: Boolean,
    items: List<Visit>,
    onItemClick: (Visit) -> Unit,
    onItemMore: (Visit) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth().background(colorResource(R.color.colorPrimaryDark))
    ) {
        Text(
            text = stringResource(R.string.visitas),
            color = colorResource(R.color.white),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (loading) {
                CircularProgressIndicator(color = colorResource(R.color.white), modifier = Modifier.padding(40.dp))
            }
            if (items.isNotEmpty()) {
                Card(
                    shape = RoundedCornerShape(topEnd = 40.dp, topStart = 40.dp, bottomEnd = 0.dp, bottomStart = 0.dp),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    modifier = Modifier.fillMaxSize().padding(top = 16.dp)
                ) {
                    LazyVerticalGrid(
                        cells = GridCells.Adaptive(4000.dp),
                        contentPadding = PaddingValues(top = 32.dp, bottom = 4.dp, end = 4.dp, start = 4.dp),
                        modifier = modifier
                    ) {
                        items(items) {
                            VisitListItem(
                                item = it,
                                modifier = Modifier.clickable { onItemClick(it) },
                                onItemMore = onItemMore
                            )
                        }
                    }
                }

            } else {
                Text(
                    text = stringResource(R.string.no_visits),
                    color = colorResource(R.color.disabled_color),
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }


}
package org.sic4change.nut4healthcentrotratamiento.ui.screens.cuadrante

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
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Cuadrant

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun CuadrantItemsListScreen(
    loading: Boolean = false,
    items: List<Cuadrant?>
) {
        var bottomSheetItem by remember { mutableStateOf<Cuadrant?>(null) }
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
            CuadrantItemBottomPreview(
                item = bottomSheetItem,
            )
        }, sheetState = sheetState) {
            CuadrantItemsList(
                loading = loading,
                items = items
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
fun CuadrantItemsList(
    loading: Boolean,
    items: List<Cuadrant?>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth().background(colorResource(R.color.colorPrimaryDark))
    ) {
        Text(
            text = stringResource(R.string.cuadrante),
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
                        contentPadding = PaddingValues(4.dp),
                        modifier = modifier
                    ) {
                        items(items) {
                            CuadrantListItem(
                                item = it,
                                modifier = Modifier.clickable {  }
                            )
                        }
                    }
                }

            }
        }
    }


}
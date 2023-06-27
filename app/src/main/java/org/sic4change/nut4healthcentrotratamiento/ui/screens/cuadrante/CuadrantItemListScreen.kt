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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Cuadrant
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.rememberMainState

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun CuadrantItemsListScreen(
    loading: Boolean = false,
    items: List<Cuadrant?>,
    onClick: (Cuadrant) -> Unit,
    onCreateVisitClick: (String) -> Unit,
    onSearch: (String) -> Unit
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
                items = items,
                onItemClick = onClick,
                onCreateVisitClick = onCreateVisitClick,
                onSearch = onSearch
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
    onItemClick: (Cuadrant) -> Unit,
    onCreateVisitClick: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val cuadranteState = rememberCuadrantsState()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.cuadrante),
            color = colorResource(R.color.colorPrimary),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = cuadranteState.filterText.value,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.full_transparent),
                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
            ),
            trailingIcon = {if (cuadranteState.filterText.value.isNotBlank()) {
                Icon(
                    Icons.Filled.Clear, null, tint = colorResource(R.color.colorPrimary),
                    modifier = Modifier.clickable {
                        cuadranteState.filterText.value = ""
                        onSearch("")
                    })} else{ null}},
            onValueChange = {
                cuadranteState.filterText.value = it
                onSearch(it) },
            textStyle = MaterialTheme.typography.h5,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences),
            leadingIcon = { Icon(Icons.Filled.Search, null, tint = colorResource(R.color.disabled_color)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            label = { Text(stringResource(R.string.searchTutorsByNameAndSurnames), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (loading) {
                CircularProgressIndicator(color = colorResource(R.color.white), modifier = Modifier.padding(40.dp))
            }
            if (items.isNotEmpty()) {
                Card(
                    shape = RoundedCornerShape(topEnd = 0.dp, topStart = 0.dp, bottomEnd = 0.dp, bottomStart = 0.dp),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    modifier = Modifier.fillMaxSize().padding(top = 16.dp)
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(top = 16.dp, bottom = 4.dp, end = 4.dp, start = 4.dp),
                        modifier = modifier
                    ) {
                        items(items) {
                            CuadrantListItem(
                                item = it,
                                modifier = Modifier.clickable {
                                    onItemClick(it!!)
                                },
                                onCreateVisitClick = onCreateVisitClick
                            )
                        }
                    }
                }

            }
        }
    }


}
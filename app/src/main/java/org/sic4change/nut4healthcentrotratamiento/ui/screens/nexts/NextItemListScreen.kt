package org.sic4change.nut4healthcentrotratamiento.ui.screens.nexts

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Cuadrant
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun NextItemsListScreen(
    loading: Boolean = false,
    items: List<Cuadrant?>,
    onClick: (Cuadrant) -> Unit,
    onCreateVisitClick: (String) -> Unit,
    onFilter: (Int) -> Unit
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
            NextItemBottomPreview(
                item = bottomSheetItem,
            )
        }, sheetState = sheetState) {
            NextItemsList(
                loading = loading,
                items = items,
                onItemClick = onClick,
                onCreateVisitClick = onCreateVisitClick,
                onFilter = onFilter
            )
        }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun NextItemsList(
    loading: Boolean,
    items: List<Cuadrant?>,
    onItemClick: (Cuadrant) -> Unit,
    onCreateVisitClick: (String) -> Unit,
    onFilter: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val nextState = rememberNextState()


    Column(
        modifier = Modifier.fillMaxWidth().background(colorResource(R.color.colorPrimaryDark))
    ) {
        Text(
            text = stringResource(R.string.next_visits),
            color = colorResource(R.color.white),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TriStateToggle(nextState.filterValue.value, onFilter = { value ->
            nextState.filterValue.value = value
            onFilter(value)
        })
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            if (loading) {
                CircularProgressIndicator(color = colorResource(R.color.white), modifier = Modifier.padding(40.dp))
            } else {
                if (items.isNotEmpty()) {
                    Card(
                        shape = RoundedCornerShape(topEnd = 0.dp, topStart = 0.dp, bottomEnd = 0.dp, bottomStart = 0.dp),
                        backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                        modifier = Modifier.fillMaxSize().padding(top = 16.dp)
                    ) {
                        LazyColumn(
                            contentPadding = PaddingValues(top = 32.dp, bottom = 4.dp, end = 4.dp, start = 4.dp),
                            modifier = modifier
                        ) {
                            items(items) {
                                NextListItem(
                                    item = it,
                                    modifier = Modifier.clickable {
                                        onItemClick(it!!)
                                    },
                                    onCreateVisitClick = onCreateVisitClick
                                )
                            }
                        }
                    }

                } else {
                    Text(
                        text = stringResource(R.string.no_visits_found),
                        color = colorResource(R.color.white),
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

}

@Composable
fun TriStateToggle(value: Int, onFilter: (Int) -> Unit) {
    val states = listOf(
        stringResource(R.string.today),
        stringResource(R.string.current_week),
        stringResource(R.string.current_month),
    )
    var selectedOption by remember {
        mutableStateOf(states[value])
    }
    val onSelectionChange = { text: String ->
        selectedOption = text
        onFilter(states.indexOf(text))
    }

    Surface(
        shape = RoundedCornerShape(24.dp),
        elevation = 0.dp,
        modifier = Modifier.background(colorResource(R.color.colorPrimary)).fillMaxWidth().padding(16.dp, 0.dp)
    ) {
        Row(
            modifier = Modifier.background(colorResource(R.color.colorPrimary)).wrapContentSize()
        ) {
            states.forEach { text->
                Text(
                    text = text,
                    color = (
                            if (text == selectedOption) {
                                colorResource(R.color.colorPrimary)
                            } else {
                                colorResource(R.color.white)
                            }),
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(24.dp))
                        .clickable {
                            onSelectionChange(text)
                        }
                        .background(
                            if (text == selectedOption) {
                                colorResource(R.color.white)
                            } else {
                                colorResource(R.color.colorPrimary)
                            }
                        )
                        .padding(
                            vertical = 12.dp,
                            horizontal = 16.dp,
                        ),
                )
            }
        }
    }
}


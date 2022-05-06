package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors

import android.os.Build
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import arrow.core.left
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.rememberMainState

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun TutorItemsListScreen(
    loading: Boolean = false,
    items: List<Tutor>,
    onClick: (Tutor) -> Unit
) {
        var bottomSheetItem by remember { mutableStateOf<Tutor?>(null) }
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

    val mainState = rememberMainState()

    Column(
        modifier = Modifier.fillMaxWidth().background(colorResource(R.color.colorPrimaryDark))
    ) {
        Text(
            text = stringResource(R.string.tutors_title),
            color = colorResource(R.color.white),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 0.dp, end = 0.dp, start = 0.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = mainState.filterText.value,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            onValueChange = {mainState.filterText.value = it},
            textStyle = MaterialTheme.typography.h5,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            label = { Text(stringResource(R.string.searchByNameAndSurnames), color = colorResource(R.color.disabled_color)) })
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
                    shape = RoundedCornerShape(topEnd = 40.dp, topStart = 40.dp, bottomEnd = 0.dp, bottomStart = 0.dp),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    modifier = Modifier.fillMaxSize().padding(top = 16.dp)
                ) {
                    LazyVerticalGrid(
                        cells = GridCells.Adaptive(4000.dp),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 0.dp, end = 0.dp, start = 0.dp),
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
            } else {
                Text(
                    text = stringResource(R.string.no_tutors),
                    color = colorResource(R.color.disabled_color),
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }


}
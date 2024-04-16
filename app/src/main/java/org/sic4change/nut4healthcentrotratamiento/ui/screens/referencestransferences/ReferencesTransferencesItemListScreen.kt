package org.sic4change.nut4healthcentrotratamiento.ui.screens.referencestransferences

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Derivation
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.ui.commons.circleLayout
import org.sic4change.nut4healthcentrotratamiento.ui.screens.derivations.rememberDerivationState
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.TutorListItem

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun ReferencesTransferencesItemsListScreen(
    type: String,
    loading: Boolean = false,
    items: List<Derivation?>,
    childs: List<Child?>,
    tutors: List<Tutor?>,
    points: List<String?>,
    onClick: (Derivation) -> Unit
) {
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

    ReferencesTransferencesItemsList(
        type = type,
        loading = loading,
        items = items,
        childs = childs,
        tutors = tutors,
        points = points,
        onItemClick = onClick
    )
}

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun ReferencesTransferencesItemsList(
    type: String,
    loading: Boolean,
    items: List<Derivation?>,
    childs: List<Child?>,
    tutors: List<Tutor?>,
    points: List<String?>,
    onItemClick: (Derivation) -> Unit,
    modifier: Modifier = Modifier
) {

    val referencesTransferencesState = rememberReferencesTransferencesState()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.align(Alignment.CenterVertically)
                    .padding(16.dp, 16.dp),
                painter = painterResource(id = R.mipmap.ic_ref),
                contentDescription = "Advise"
            )

            Text(
                color = colorResource(R.color.error),
                text = if (type == "Referred") stringResource(R.string.search_reference) else stringResource(R.string.search_transference),
                style = MaterialTheme.typography.h5,
                maxLines = 2,
                modifier = Modifier.weight(1f)
            )

            Image(
                modifier = Modifier.align(Alignment.CenterVertically)
                    .padding(16.dp, 16.dp).clickable {
                        referencesTransferencesState.expanded.value = !referencesTransferencesState.expanded.value
                    },
                painter = painterResource(id = R.mipmap.ic_lens),
                contentDescription = "Search"
            )
        }

        if (referencesTransferencesState.expanded.value) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp),
                    expanded = referencesTransferencesState.expandedOrigin.value,
                    onExpandedChange = {
                        referencesTransferencesState.expandedOrigin.value = !referencesTransferencesState.expandedOrigin.value
                    }
                ) {
                    TextField(
                        value = referencesTransferencesState.originName.value,
                        onValueChange = { referencesTransferencesState.originName.value = it },
                        enabled = false,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = referencesTransferencesState.expandedOrigin.value
                            )
                        },
                        textStyle = MaterialTheme.typography.h5.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(colorResource(R.color.full_transparent), shape = RoundedCornerShape(8.dp))
                            .border(1.dp, colorResource(R.color.colorPrimary), shape = RoundedCornerShape(8.dp)),
                        leadingIcon = {
                            Image(
                                painter = painterResource(R.mipmap.ic_derivation_centre_blue),
                                contentDescription = null,
                                modifier =  Modifier.size(24.dp)
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.gray_light),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.original_centre),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )
                    ExposedDropdownMenu(
                        expanded = referencesTransferencesState.expandedOrigin.value,
                        onDismissRequest = {
                            referencesTransferencesState.expandedOrigin.value = false
                        }
                    ) {
                        points.forEach { selectedOrigin ->
                            DropdownMenuItem(
                                onClick = {
                                    referencesTransferencesState.originName.value = selectedOrigin!!
                                    referencesTransferencesState.expandedOrigin.value = false
                                }
                            ) {
                                Text(text = selectedOrigin!!, color = colorResource(R.color.colorPrimary))
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ){
                    TextField(
                        value = referencesTransferencesState.codeNumber.value,
                        onValueChange = { referencesTransferencesState.codeNumber.value = it },
                        textStyle = MaterialTheme.typography.h5.copy(color = colorResource(R.color.colorPrimary)),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 8.dp, 16.dp, 16.dp)
                            .background(
                                colorResource(R.color.full_transparent),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                1.dp,
                                colorResource(R.color.colorPrimary),
                                shape = RoundedCornerShape(8.dp)
                            ).weight(1f),
                        leadingIcon = {
                            Image(
                                painter = painterResource(R.mipmap.ic_code),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.gray_light),
                            cursorColor = colorResource(R.color.colorPrimary),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.reference_number),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )
                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp).weight(1f),
                    expanded = referencesTransferencesState.expandedYear.value,
                    onExpandedChange = {
                        referencesTransferencesState.expandedYear.value =
                            !referencesTransferencesState.expandedYear.value
                    }
                ) {
                    TextField(
                        value = referencesTransferencesState.year.value,
                        onValueChange = { referencesTransferencesState.year.value = it },
                        enabled = false,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = referencesTransferencesState.expandedYear.value
                            )
                        },
                        textStyle = MaterialTheme.typography.h5.copy(color = colorResource(R.color.colorPrimary)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 8.dp, 16.dp, 16.dp)
                            .background(
                                colorResource(R.color.full_transparent),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                1.dp,
                                colorResource(R.color.colorPrimary),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        leadingIcon = {
                            Image(
                                painter = painterResource(R.mipmap.ic_calendar),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(R.color.gray_light),
                            cursorColor = colorResource(R.color.full_transparent),
                            disabledTextColor = colorResource(R.color.colorPrimary),
                            disabledIndicatorColor = colorResource(R.color.full_transparent),
                            disabledLabelColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.full_transparent),
                            unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                        ),
                        label = {
                            Text(
                                stringResource(R.string.year),
                                color = colorResource(R.color.disabled_color)
                            )
                        }
                    )
                    ExposedDropdownMenu(
                        expanded = referencesTransferencesState.expandedYear.value,
                        onDismissRequest = {
                            referencesTransferencesState.expandedYear.value = false
                        }
                    ) {
                        referencesTransferencesState.getYears().forEach { selectedYear ->
                            DropdownMenuItem(
                                onClick = {
                                    referencesTransferencesState.year.value = selectedYear
                                    referencesTransferencesState.expandedYear.value = false
                                }
                            ) {
                                Text(
                                    text = selectedYear,
                                    color = colorResource(R.color.colorPrimary)
                                )
                            }
                        }
                    }
                }
            }

            if (referencesTransferencesState.enableSarch()) {
                Image(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(16.dp, 16.dp).clickable {

                        },
                    painter = painterResource(id = R.mipmap.ic_ref),
                    contentDescription = "Advise"
                )

                Text(
                    text = stringResource(R.string.search_result),
                    color = colorResource(R.color.colorPrimary),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp, 16.dp, 16.dp)
                )

                val filterItem = referencesTransferencesState.getReferenceTransferenceFound(items)
                if (filterItem != null) {
                    ReferenceTransferenceListItem(
                        derivation = filterItem!!,
                        child = childs.find { child -> child?.id == filterItem!!.childId },
                        tutor = tutors.find { tutor -> tutor?.id == filterItem!!.fefaId },
                        modifier = Modifier.clickable { onItemClick(filterItem)},
                        onClickDetail = onItemClick
                    )
                }


            }

            }
        }

        Column(
            modifier = Modifier.fillMaxWidth().background(colorResource(R.color.gray_light))
        ) {
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                startIndent = 0.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (type == "Referred") stringResource(R.string.refernce_list) else stringResource(R.string.trasferred_list),
                color = colorResource(R.color.colorPrimary),
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = modifier.fillMaxSize().background(colorResource(R.color.gray_light)),
                contentAlignment = Alignment.TopCenter
            ) {
                if (loading) {
                    CircularProgressIndicator(color = colorResource(R.color.colorPrimary), modifier = Modifier.padding(40.dp))
                } else {
                    if (items.isNotEmpty()) {

                        LazyColumn(
                            modifier = Modifier.background(colorResource(R.color.gray_light))
                        ) {
                            items(items) {
                                ReferenceTransferenceListItem(
                                    derivation = it!!,
                                    child = childs.find { child -> child?.id == it.childId },
                                    tutor = tutors.find { tutor -> tutor?.id == it.fefaId },
                                    modifier = Modifier.clickable { onItemClick(it) },
                                    onClickDetail = onItemClick
                                )
                            }

                        }

                    } else {
                        Text(
                            text = if (type == "Referred") stringResource(R.string.no_refernce_list) else stringResource(R.string.no_trasferred_list),
                            color = colorResource(R.color.colorPrimary),
                            style = MaterialTheme.typography.caption,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

            }

        }
    }



}
package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.detail


import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.os.LocaleListCompat
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Complication
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.ui.commons.CheckNUT4H
import org.sic4change.nut4healthcentrotratamiento.ui.commons.CheckNUT4HDisabled
import org.sic4change.nut4healthcentrotratamiento.ui.commons.SimpleRulerViewer
import org.sic4change.nut4healthcentrotratamiento.ui.commons.formatStatus
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.VisitState
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.create.ItemListComplications
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun VisitItemDetailScreen(
    visitState: VisitState, loading: Boolean = false,
    visitItem: Visit?, onEditClick: (Visit) -> Unit,
    onDeleteClick: (String) -> Unit) {

    if (loading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = colorResource(R.color.colorPrimaryDark))
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (visitItem != null) {
            VisitItemDetailScaffold(
                visitState = visitState,
                visitItem = visitItem,
                onClickEdit = onEditClick,
                onClickDelete = onDeleteClick
            ) { padding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding)
                ) {
                    item {
                        Header(visitState = visitState, loading = loading)
                    }

                }
            }

        }

    }

}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ResourceAsColor")
@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@ExperimentalCoilApi
@Composable
private fun Header(loading: Boolean, visitState: VisitState, ) {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    val dateString = simpleDateFormat.format(visitState.childDateMillis.value)

    val monthsBetween = ChronoUnit.MONTHS.between(
        YearMonth.from(LocalDate.parse(dateString)), YearMonth.from(LocalDate.now())
    )
    if (!loading) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = visitState.height.value.toString(),
                enabled = false,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.colorPrimary),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.colorAccent),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.colorAccent),
                    unfocusedIndicatorColor = colorResource(R.color.colorAccent),
                ),
                onValueChange = {},
                textStyle = MaterialTheme.typography.h5,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(Icons.Filled.Height, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                label = { Text(stringResource(R.string.height), color = colorResource(R.color.disabled_color)) })
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = visitState.weight.value.toString(),
                enabled = false,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.colorPrimary),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.colorAccent),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.colorAccent),
                    unfocusedIndicatorColor = colorResource(R.color.colorAccent),
                ),
                onValueChange = {},
                textStyle = MaterialTheme.typography.h5,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(painterResource(R.mipmap.ic_brothers), null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                label = { Text(stringResource(R.string.weight), color = colorResource(R.color.disabled_color)) })
            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(visible = ((visitState.armCircunference.value != 30.0) && (monthsBetween >= 6 && monthsBetween <= 60))) {
                if (visitState.armCircunference.value < 11.5) {
                    TextField(value = visitState.armCircunference.value.toString(),
                        enabled = false,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.error),
                            backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            cursorColor = colorResource(R.color.error),
                            disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.error),
                            unfocusedIndicatorColor = colorResource(R.color.error),
                        ),
                        onValueChange = {}, readOnly = true,
                        textStyle = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        leadingIcon = {
                            Icon(Icons.Filled.MultipleStop, null, tint = colorResource(R.color.error),  modifier = Modifier.clickable { /* .. */})},
                        label = { Text(stringResource(R.string.arm_circunference), color = colorResource(R.color.disabled_color)) })
                }  else if (visitState.armCircunference.value >= 11.5 && visitState.armCircunference.value <= 12.5) {
                    TextField(value = visitState.armCircunference.value.toString(),
                        enabled = false,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.orange),
                            backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            cursorColor = colorResource(R.color.orange),
                            disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.orange),
                            unfocusedIndicatorColor = colorResource(R.color.orange),
                        ),
                        onValueChange = {}, readOnly = true,
                        textStyle = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        leadingIcon = {
                            Icon(Icons.Filled.MultipleStop, null, tint = colorResource(R.color.orange),  modifier = Modifier.clickable { /* .. */})},
                        label = { Text(stringResource(R.string.arm_circunference), color = colorResource(R.color.disabled_color)) })
                } else {
                    TextField(value = visitState.armCircunference.value.toString(),
                        enabled = false,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorAccent),
                            backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            cursorColor = colorResource(R.color.colorAccent),
                            disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.colorAccent),
                            unfocusedIndicatorColor = colorResource(R.color.colorAccent),
                        ),
                        onValueChange = {}, readOnly = true,
                        textStyle = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        leadingIcon = {
                            Icon(Icons.Filled.MultipleStop, null, tint = colorResource(R.color.colorAccent),  modifier = Modifier.clickable { /* .. */})},
                        label = { Text(stringResource(R.string.arm_circunference), color = colorResource(R.color.disabled_color)) })
                }

            }

            AnimatedVisibility(visible = (visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty() && (monthsBetween >= 6 && monthsBetween <= 60))) {
                Spacer(modifier = Modifier.height(16.dp))
            }

            TextField(
                enabled = false,
                readOnly = true,
                value = if (visitState.selectedEdema.value != "") visitState.selectedEdema.value else stringArrayResource(R.array.edemaOptions)[0],
                onValueChange = {
                    visitState.selectedEdema.value = it
                },
                textStyle = MaterialTheme.typography.h5,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.colorPrimary),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.colorAccent),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.colorAccent),
                    unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(painterResource(R.mipmap.ic_edema), null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                label = { Text(stringResource(R.string.edema), color = colorResource(R.color.disabled_color)) }
            )


            Spacer(modifier = Modifier.height(16.dp))

            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
                elevation = 0.dp,
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey)
            )
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(16.dp)
                ) {
                    Text(text = stringResource(R.string.complications), color = colorResource(R.color.disabled_color),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp))

                    visitState.complications.value.forEach { complication ->
                        ItemListComplicationsDisabled(
                            complication = complication,
                            checked = complication.selected,
                            onCheckedChange = {
                                val complicationsToUpdate: MutableList<Complication> = mutableListOf()
                                visitState.complications.value.forEach { item ->
                                    if (item.id == complication.id) {
                                        item.selected = it
                                    }
                                    complicationsToUpdate.add(item)
                                }
                                visitState.complications.value = mutableListOf()
                                visitState.complications.value.addAll(complicationsToUpdate)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(visible = (visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty() )) {
                var statusFormated = ""
                if (visitState.status.value == "Normopeso" || visitState.status.value == stringResource(R.string.normopeso)) {
                    statusFormated = stringResource(R.string.normopeso)
                } else if (visitState.status.value == "Peso Objetivo" || visitState.status.value == stringResource(R.string.objetive_weight)) {
                    statusFormated = stringResource(R.string.objetive_weight)
                } else if (visitState.status.value == "Aguda Moderada" || visitState.status.value == stringResource(R.string.aguda_moderada)) {
                    statusFormated = stringResource(R.string.aguda_moderada)
                } else {
                    statusFormated = stringResource(R.string.aguda_severa)
                }

                if (visitState.armCircunference.value < 11.5) {
                    statusFormated = stringResource(R.string.aguda_severa)
                } else if (visitState.armCircunference.value >= 11.5 && visitState.armCircunference.value <= 12.5 &&
                    (statusFormated == stringResource(R.string.normopeso) || statusFormated == stringResource(R.string.objetive_weight))) {
                    statusFormated = stringResource(R.string.aguda_moderada)
                }

                visitState.status.value = statusFormated

                if (visitState.status.value == stringResource(R.string.normopeso)) {
                    TextField(value = visitState.status.value,
                        enabled = false,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorAccent),
                            backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            cursorColor = colorResource(R.color.colorAccent),
                            disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.colorAccent),
                            unfocusedIndicatorColor = colorResource(R.color.colorAccent),
                        ),
                        onValueChange = {}, readOnly = true,
                        textStyle = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        leadingIcon = {
                            Icon(Icons.Filled.FolderOpen, null, tint = colorResource(R.color.colorAccent),  modifier = Modifier.clickable { /* .. */})},
                        label = { Text(stringResource(R.string.status), color = colorResource(R.color.disabled_color)) })
                } else if (visitState.status.value == stringResource(R.string.objetive_weight)) {
                    TextField(value = visitState.status.value,
                        enabled = false,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.colorPrimary),
                            backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            cursorColor = colorResource(R.color.colorPrimary),
                            disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.colorPrimary),
                            unfocusedIndicatorColor = colorResource(R.color.colorPrimary),
                        ),
                        onValueChange = {}, readOnly = true,
                        textStyle = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        leadingIcon = {
                            Icon(Icons.Filled.FolderOpen, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                        label = { Text(stringResource(R.string.status), color = colorResource(R.color.disabled_color)) })
                } else if (visitState.status.value == stringResource(R.string.aguda_moderada)) {
                    TextField(value = visitState.status.value,
                        enabled = false,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.orange),
                            backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            cursorColor = colorResource(R.color.orange),
                            disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.orange),
                            unfocusedIndicatorColor = colorResource(R.color.orange),
                        ),
                        onValueChange = {}, readOnly = true,
                        textStyle = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        leadingIcon = {
                            Icon(Icons.Filled.FolderOpen, null, tint = colorResource(R.color.orange),  modifier = Modifier.clickable { /* .. */})},
                        label = { Text(stringResource(R.string.status), color = colorResource(R.color.disabled_color)) })
                } else {
                    TextField(value = statusFormated,
                        enabled = false,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = colorResource(R.color.error),
                            backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            cursorColor = colorResource(R.color.error),
                            disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            focusedIndicatorColor = colorResource(R.color.error),
                            unfocusedIndicatorColor = colorResource(R.color.error),
                        ),
                        onValueChange = {}, readOnly = true,
                        textStyle = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        leadingIcon = {
                            Icon(Icons.Filled.FolderOpen, null, tint = colorResource(R.color.error),  modifier = Modifier.clickable { /* .. */})},
                        label = { Text(stringResource(R.string.status), color = colorResource(R.color.disabled_color)) })
                }

            }

            AnimatedVisibility(visible = (visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                    && visitState.status.value != stringResource(R.string.normopeso) && visitState.status.value != stringResource(R.string.objetive_weight))) {
                Spacer(modifier = Modifier.height(16.dp))
            }

            AnimatedVisibility(visible = (visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                    && visitState.status.value != stringResource(R.string.normopeso) && visitState.status.value != stringResource(R.string.objetive_weight))) {
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                    elevation = 0.dp,
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp)
                    ) {
                        Text(text = stringResource(R.string.symtoms), color = colorResource(R.color.disabled_color),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 0.dp),)

                        AnimatedVisibility(visitState.status.value == stringResource(R.string.aguda_severa)) {
                            TextField(
                                enabled = false,
                                readOnly = true,
                                value = if (visitState.selectedRespiration.value == "") stringArrayResource(R.array.respirationOptions)[0] else visitState.selectedRespiration.value,
                                onValueChange = {},
                                textStyle = MaterialTheme.typography.h5,
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = colorResource(R.color.colorPrimary),
                                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    cursorColor = colorResource(R.color.colorAccent),
                                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    focusedIndicatorColor = colorResource(R.color.colorAccent),
                                    unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                leadingIcon = {
                                    Icon(Icons.Filled.Air, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                                label = { Text(stringResource(R.string.respiration), color = colorResource(R.color.disabled_color)) }
                            )
                        }

                        AnimatedVisibility(visitState.status.value == stringResource(R.string.aguda_severa)) {
                                TextField(
                                    enabled = false,
                                    readOnly = true,
                                    value = if (visitState.selectedApetit.value == "") stringArrayResource(R.array.apetitOptions)[0] else visitState.selectedApetit.value,
                                    onValueChange = {},
                                    textStyle = MaterialTheme.typography.h5,
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = colorResource(R.color.colorPrimary),
                                        backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                        cursorColor = colorResource(R.color.colorAccent),
                                        disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                        focusedIndicatorColor = colorResource(R.color.colorAccent),
                                        unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    leadingIcon = {
                                        Icon(Icons.Filled.BreakfastDining, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                                    label = { Text(stringResource(R.string.apetit), color = colorResource(R.color.disabled_color)) }
                                )

                            }


                            TextField(
                                readOnly = true,
                                value = if (visitState.selectedInfection.value == "") stringArrayResource(R.array.yesnooptions)[1] else visitState.selectedInfection.value,
                                onValueChange = {},
                                textStyle = MaterialTheme.typography.h5,
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = colorResource(R.color.colorPrimary),
                                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    cursorColor = colorResource(R.color.colorAccent),
                                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    focusedIndicatorColor = colorResource(R.color.colorAccent),
                                    unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                leadingIcon = {
                                    Icon(Icons.Filled.RemoveRedEye, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                                label = { Text(stringResource(R.string.infection), color = colorResource(R.color.disabled_color)) }
                            )

                            TextField(
                                enabled = false,
                                readOnly = true,
                                value = if (visitState.selectedEyes.value == "") stringArrayResource(R.array.yesnooptions)[1] else visitState.selectedEyes.value,
                                onValueChange = {},
                                textStyle = MaterialTheme.typography.h5,
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = colorResource(R.color.colorPrimary),
                                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    cursorColor = colorResource(R.color.colorAccent),
                                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    focusedIndicatorColor = colorResource(R.color.colorAccent),
                                    unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                leadingIcon = {
                                    Icon(Icons.Filled.RemoveRedEye, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                                label = { Text(stringResource(R.string.eyes), color = colorResource(R.color.disabled_color)) }
                            )



                            TextField(
                                enabled = false,
                                readOnly = true,
                                value = if (visitState.selectedDeshidratation.value == "") stringArrayResource(R.array.yesnooptions)[1] else visitState.selectedDeshidratation.value,
                                onValueChange = {},
                                textStyle = MaterialTheme.typography.h5,
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = colorResource(R.color.colorPrimary),
                                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    cursorColor = colorResource(R.color.colorAccent),
                                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    focusedIndicatorColor = colorResource(R.color.colorAccent),
                                    unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                leadingIcon = {
                                    Icon(Icons.Filled.WaterDrop, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                                label = { Text(stringResource(R.string.deshidratation), color = colorResource(R.color.disabled_color)) }
                            )



                            TextField(
                                enabled = false,
                                readOnly = true,
                                value = if (visitState.selectedVomitos.value == "") stringArrayResource(R.array.frecuencyOptions)[0] else visitState.selectedVomitos.value,
                                onValueChange = {},
                                textStyle = MaterialTheme.typography.h5,
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = colorResource(R.color.colorPrimary),
                                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    cursorColor = colorResource(R.color.colorAccent),
                                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    focusedIndicatorColor = colorResource(R.color.colorAccent),
                                    unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                leadingIcon = {
                                    Icon(Icons.Filled.PersonalInjury, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                                label = { Text(stringResource(R.string.vomits), color = colorResource(R.color.disabled_color)) }
                            )

                            TextField(
                                enabled = false,
                                readOnly = true,
                                value = if (visitState.selectedDiarrea.value == "") stringArrayResource(R.array.frecuencyOptions)[0] else visitState.selectedDiarrea.value,
                                onValueChange = {},
                                textStyle = MaterialTheme.typography.h5,
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = colorResource(R.color.colorPrimary),
                                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    cursorColor = colorResource(R.color.colorAccent),
                                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    focusedIndicatorColor = colorResource(R.color.colorAccent),
                                    unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                leadingIcon = {
                                    Icon(Icons.Filled.PersonalInjury, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                                label = { Text(stringResource(R.string.diarrea), color = colorResource(R.color.disabled_color)) }
                            )

                            TextField(
                                enabled = false,
                                readOnly = true,
                                value = if (visitState.selectedFiebre.value == "") stringArrayResource(R.array.frecuencyOptions)[0] else visitState.selectedFiebre.value,
                                onValueChange = {},
                                textStyle = MaterialTheme.typography.h5,
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = colorResource(R.color.colorPrimary),
                                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    cursorColor = colorResource(R.color.colorAccent),
                                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    focusedIndicatorColor = colorResource(R.color.colorAccent),
                                    unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                leadingIcon = {
                                    Icon(Icons.Filled.PersonalInjury, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                                label = { Text(stringResource(R.string.fiebre), color = colorResource(R.color.disabled_color)) }
                            )


                        AnimatedVisibility(visitState.status.value == stringResource(R.string.aguda_severa)) {
                                TextField(
                                    enabled = false,
                                    readOnly = true,
                                    value = if (visitState.selectedTos.value == "") stringArrayResource(R.array.yesnooptions)[1] else visitState.selectedTos.value,
                                    onValueChange = {},
                                    textStyle = MaterialTheme.typography.h5,
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = colorResource(R.color.colorPrimary),
                                        backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                        cursorColor = colorResource(R.color.colorAccent),
                                        disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                        focusedIndicatorColor = colorResource(R.color.colorAccent),
                                        unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    leadingIcon = {
                                        Icon(Icons.Filled.PersonalInjury, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                                    label = { Text(stringResource(R.string.tos), color = colorResource(R.color.disabled_color)) }
                                )
                            }

                        TextField(
                            enabled = false,
                            readOnly = true,
                            value = if (visitState.selectedTemperature.value == "") "-" else visitState.selectedTemperature.value,
                            onValueChange = {},
                            textStyle = MaterialTheme.typography.h5,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = colorResource(R.color.colorPrimary),
                                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                cursorColor = colorResource(R.color.colorAccent),
                                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                focusedIndicatorColor = colorResource(R.color.colorAccent),
                                unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            ),
                            modifier = Modifier
                                .fillMaxWidth(),
                            leadingIcon = {
                                Icon(Icons.Filled.Thermostat, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                            label = { Text(stringResource(R.string.temperature), color = colorResource(R.color.disabled_color)) }
                        )
                        }

                    }
                }

            AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                    && visitState.status.value == stringResource(R.string.aguda_moderada)) {
                Spacer(modifier = Modifier.height(16.dp))
            }

            AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                    && visitState.status.value == stringResource(R.string.aguda_moderada)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    elevation = 0.dp,
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(0.dp, 16.dp)
                    ) {

                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier.padding(horizontal = 16.dp)
                                .fillMaxSize()
                                .align(Alignment.CenterHorizontally)
                        ) {
                            CheckNUT4HDisabled(text = stringResource(id = R.string.vitamineAVaccinated), visitState.vitamineAVaccinated.value)
                        }

                        AnimatedVisibility(visitState.vitamineAVaccinated.value) {
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        AnimatedVisibility(visitState.vitamineAVaccinated.value) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(0.dp, 0.dp)
                            ) {
                                Text(stringResource(R.string.vitamine_dosis), color = colorResource(R.color.colorPrimary))
                                if (visitState.weight.value.toDouble() in 6.0..8.0 || (monthsBetween >= 6 && monthsBetween <= 11)) {
                                    Text(stringResource(R.string.vitamine_blue), color = colorResource(R.color.colorPrimary))
                                } else if (visitState.weight.value.toDouble() > 8.0 || (monthsBetween >= 12)) {
                                    Text(stringResource(R.string.vitamine_red), color = colorResource(R.color.colorPrimary))
                                }
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                    && (visitState.status.value == stringResource(R.string.aguda_moderada)
                    && visitState.visitsSize.value == 0) || (visitState.status.value == stringResource(R.string.aguda_severa)
                    && visitState.visitsSize.value == 1)) {
                Spacer(modifier = Modifier.height(16.dp))
            }

            AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                    && (visitState.status.value == stringResource(R.string.aguda_moderada)
                    && visitState.visitsSize.value == 0) || (visitState.status.value == stringResource(R.string.aguda_severa)
                    && visitState.visitsSize.value == 1)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    elevation = 0.dp,
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(0.dp, 16.dp)
                    ) {
                        if ((monthsBetween >= 12 && monthsBetween < 24)) {
                            Text(
                                stringResource(R.string.admin_dosis),
                                color = colorResource(R.color.colorPrimary)
                            )
                            Text(
                                stringResource(R.string.abendazol_400_half),
                                color = colorResource(R.color.colorPrimary)
                            )
                            Text("o", color = colorResource(R.color.colorPrimary))
                            Text(
                                stringResource(R.string.mebendazol_400_half),
                                color = colorResource(R.color.colorPrimary)
                            )
                        } else if ((monthsBetween >= 24)) {
                            Text(
                                stringResource(R.string.admin_dosis),
                                color = colorResource(R.color.colorPrimary)
                            )
                            Text(
                                stringResource(R.string.abendazol_400_full),
                                color = colorResource(R.color.colorPrimary)
                            )
                            Text("o", color = colorResource(R.color.colorPrimary))
                            Text(
                                stringResource(R.string.mebendazol_400_full),
                                color = colorResource(R.color.colorPrimary)
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                    && visitState.status.value == stringResource(R.string.aguda_moderada)) {
                Spacer(modifier = Modifier.height(16.dp))
            }

            AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                    && visitState.status.value == stringResource(R.string.aguda_moderada)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    elevation = 0.dp,
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            8.dp,
                            Alignment.CenterVertically
                        ),
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(0.dp, 16.dp)
                    ) {
                        Text(
                            stringResource(R.string.hierro_folico),
                            color = colorResource(R.color.colorPrimary)
                        )
                        if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() < 10.0)) {
                            Text(
                                stringResource(R.string.capsules_hierro_folico_one),
                                color = colorResource(R.color.colorPrimary)
                            )
                        } else {
                            Text(
                                stringResource(R.string.capsules_hierro_folico_tow),
                                color = colorResource(R.color.colorPrimary)
                            )
                        }
                        Box(
                            modifier = Modifier.padding(horizontal = 16.dp)
                                .fillMaxSize()
                                .align(Alignment.CenterHorizontally)
                        ) {
                            CheckNUT4HDisabled(text = stringResource(id = R.string.capsules_hierro_folico_checked), visitState.capsulesFerro.value)
                        }

                    }
                }
            }



            AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                    && visitState.status.value == stringResource(R.string.aguda_moderada)
                    && monthsBetween >= 9) {
                Spacer(modifier = Modifier.height(16.dp))
            }

            AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                    && visitState.status.value == stringResource(R.string.aguda_moderada)
                    && monthsBetween >= 9) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    elevation = 0.dp,
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(0.dp, 16.dp)
                    ) {
                        TextField(
                            enabled = false,
                            readOnly = true,
                            value = visitState.selectedCartilla.value,
                            onValueChange = {
                                visitState.selectedCartilla.value = it
                            },
                            textStyle = MaterialTheme.typography.h5,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = colorResource(R.color.colorPrimary),
                                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                cursorColor = colorResource(R.color.colorAccent),
                                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                focusedIndicatorColor = colorResource(R.color.colorAccent),
                                unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                            ),
                            modifier = Modifier
                                .fillMaxWidth(),
                            leadingIcon = {
                                Icon(Icons.Filled.Book, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                            label = { Text(stringResource(R.string.cartilla), color = colorResource(R.color.disabled_color)) }
                        )
                        AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                                && visitState.status.value == stringResource(R.string.aguda_moderada)
                                && monthsBetween >= 9
                                && visitState.selectedCartilla.value == stringArrayResource(id = R.array.yesnooptions)[0]) {
                            TextField(
                                enabled = false,
                                readOnly = true,
                                value = visitState.selectedRubeola.value,
                                onValueChange = {
                                    visitState.selectedRubeola.value = it
                                },
                                textStyle = MaterialTheme.typography.h5,
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = colorResource(R.color.colorPrimary),
                                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    cursorColor = colorResource(R.color.colorAccent),
                                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                    focusedIndicatorColor = colorResource(R.color.colorAccent),
                                    unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                leadingIcon = {
                                    Icon(Icons.Filled.Vaccines, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                                label = { Text(stringResource(R.string.rubeola), color = colorResource(R.color.disabled_color)) }
                            )
                            Text(text = visitState.selectedRubeola.value, color = colorResource(R.color.colorPrimary))

                        }

                        AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                                && visitState.status.value == stringResource(R.string.aguda_moderada)
                                && monthsBetween >= 9
                                && visitState.selectedCartilla.value == stringArrayResource(id = R.array.yesnooptions)[1]) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(0.dp, 16.dp)
                            ) {
                                Text(text = stringResource(R.string.must_rubeola), color = colorResource(R.color.colorPrimary))
                            }

                        }

                        AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                                && visitState.status.value == stringResource(R.string.aguda_moderada)
                                && monthsBetween >= 9
                                && visitState.selectedCartilla.value == stringArrayResource(id = R.array.yesnooptions)[0]
                                && visitState.selectedRubeola.value == stringArrayResource(id = R.array.yesnooptions)[1]) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 16.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.must_rubeola),
                                    color = colorResource(R.color.colorPrimary),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth())
                            }
                        }
                    }
                }

            }

            AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                    && visitState.status.value == stringResource(R.string.aguda_severa)
                    && visitState.visitsSize.value == 0) {
                Spacer(modifier = Modifier.height(16.dp))
            }

            AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                    && visitState.status.value == stringResource(R.string.aguda_severa)
                    && visitState.visitsSize.value == 0) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    elevation = 0.dp,
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            8.dp,
                            Alignment.CenterVertically
                        ),
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(0.dp, 16.dp)
                    ) {
                        Text(
                            stringResource(R.string.amoxicilina),
                            color = colorResource(R.color.colorPrimary)
                        )
                        if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() < 5.0)) {
                            Text(
                                stringResource(R.string.amoxicilina_125),
                                color = colorResource(R.color.colorPrimary)
                            )
                        } else if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() >= 5.0 && visitState.weight.value.toDouble() < 10.0)) {
                            Text(
                                stringResource(R.string.amoxicilina_250),
                                color = colorResource(R.color.colorPrimary)
                            )
                        } else if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() >= 10.0 && visitState.weight.value.toDouble() < 20.0)) {
                            Text(
                                stringResource(R.string.amoxicilina_500),
                                color = colorResource(R.color.colorPrimary)
                            )
                        } else if ((visitState.weight.value.isNotEmpty() && visitState.weight.value.toDouble() >= 20.0 && visitState.weight.value.toDouble() < 35.0)) {
                            Text(
                                stringResource(R.string.amoxicilina_750),
                                color = colorResource(R.color.colorPrimary)
                            )
                        } else {
                            Text(
                                stringResource(R.string.amoxicilina_1000),
                                color = colorResource(R.color.colorPrimary)
                            )
                        }
                        Box(
                            modifier = Modifier.padding(horizontal = 16.dp)
                                .fillMaxSize()
                                .align(Alignment.CenterHorizontally)
                        ) {
                            CheckNUT4HDisabled(text = stringResource(id = R.string.amoxicilina_question), visitState.amoxicilina.value)
                        }
                        TextField(value = visitState.othersTratments.value.toString(),
                            enabled = false,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = colorResource(R.color.colorPrimary),
                                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                cursorColor = colorResource(R.color.colorAccent),
                                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                                focusedIndicatorColor = colorResource(R.color.colorAccent),
                                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
                            ),
                            onValueChange = {
                                visitState.othersTratments.value = it
                            },
                            textStyle = MaterialTheme.typography.h5,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 0.dp),
                            leadingIcon = {
                                Icon(Icons.Filled.Medication, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                            label = { Text(stringResource(R.string.another_tratements), color = colorResource(R.color.disabled_color)) })

                    }
                }
            }



            Spacer(modifier = Modifier.height(16.dp))

            TextField(value = visitState.observations.value,
                enabled = false,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.colorPrimary),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.colorAccent),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.colorAccent),
                    unfocusedIndicatorColor = colorResource(R.color.colorAccent),
                ),
                onValueChange = {visitState.observations.value = it},
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(Icons.Filled.Edit, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                label = { Text(stringResource(R.string.observations), color = colorResource(R.color.disabled_color)) })


            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(visible = (
                    visitState.weight.value.isNotEmpty()
                            && visitState.height.value.isNotEmpty()
                            && visitState.status.value == stringResource(R.string.aguda_moderada)
                    )) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.plumpy_one),
                        color = colorResource(R.color.colorAccent),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth())
                    Text(
                        text = stringResource(R.string.plumpy_fiveteeen),
                        color = colorResource(R.color.colorAccent),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth())
                }

            }

            AnimatedVisibility(visible = (
                    visitState.weight.value.isNotEmpty()
                            && visitState.height.value.isNotEmpty()
                            && visitState.status.value == stringResource(R.string.aguda_severa)
                    )) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.plumpy_one),
                        color = colorResource(R.color.colorAccent),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth())

                    if (visitState.weight.value.toDouble() >= 3.0 && visitState.weight.value.toDouble() < 3.5) {
                        Text(
                            text = stringResource(R.string.plumpy_mas_8),
                            color = colorResource(R.color.colorAccent),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth())
                    } else if (visitState.weight.value.toDouble() >= 3.5 && visitState.weight.value.toDouble() < 5.0) {
                        Text(
                            text = stringResource(R.string.plumpy_mas_10),
                            color = colorResource(R.color.colorAccent),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth())
                    } else if (visitState.weight.value.toDouble() >= 5.0 && visitState.weight.value.toDouble() < 7.0) {
                        Text(
                            text = stringResource(R.string.plumpy_mas_15),
                            color = colorResource(R.color.colorAccent),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth())
                    } else if (visitState.weight.value.toDouble() >= 7.0 && visitState.weight.value.toDouble() < 10.0) {
                        Text(
                            text = stringResource(R.string.plumpy_mas_20),
                            color = colorResource(R.color.colorAccent),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth())
                    } else if (visitState.weight.value.toDouble() >= 10.0 && visitState.weight.value.toDouble() < 15.0) {
                        Text(
                            text = stringResource(R.string.plumpy_mas_30),
                            color = colorResource(R.color.colorAccent),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth())
                    } else if (visitState.weight.value.toDouble() >= 15.0 && visitState.weight.value.toDouble() < 20.0) {
                        Text(
                            text = stringResource(R.string.plumpy_mas_35),
                            color = colorResource(R.color.colorAccent),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth())
                    } else if (visitState.weight.value.toDouble() >= 20.0 && visitState.height.value.toDouble() < 30.0) {
                        Text(
                            text = stringResource(R.string.plumpy_mas_40),
                            color = colorResource(R.color.colorAccent),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth())
                    } else if (visitState.weight.value.toDouble() >= 30.0 && visitState.weight.value.toDouble() < 40.0) {
                        Text(
                            text = stringResource(R.string.plumpy_mas_50),
                            color = colorResource(R.color.colorAccent),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth())
                    } else if (visitState.weight.value.toDouble() >= 40.0 && visitState.weight.value.toDouble() <= 60.0) {
                        Text(
                            text = stringResource(R.string.plumpy_mas_55),
                            color = colorResource(R.color.colorAccent),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth())
                    }
                }

            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}

@Composable
fun ItemListComplicationsDisabled(complication: Complication, checked: Boolean, onCheckedChange : (Boolean) -> Unit) {

    val language = LocaleListCompat.getDefault()[0]!!.toLanguageTag()
    var complicationTag = ""
    if (language.contains("es-")) {
        complicationTag = complication.name
    } else if(language.contains("en-")) {
        complicationTag = complication.name_en
    } else {
        complicationTag = complication.name_fr
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,

        ) {

        Checkbox(
            enabled = false,
            checked = true,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(colorResource(R.color.colorPrimaryDark)),
        )

        Text(
            color = colorResource(R.color.colorPrimary),
            text = complicationTag,
            style = MaterialTheme.typography.body1,
        )

    }

}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageDeleteVisit(showDialog: Boolean, setShowDialog: () -> Unit, visitId: String, caseId: String,
                       onDeleteVisit: (String) -> Unit, onDeleteVisitSelected: (String) -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text(stringResource(R.string.nut4health))
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                    onClick = {
                        setShowDialog()
                        onDeleteVisit(visitId)
                        onDeleteVisitSelected(caseId)
                    },
                ) {
                    Text(stringResource(R.string.accept), color = colorResource(R.color.white))
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                    onClick = {
                        setShowDialog()
                    },
                ) {
                    Text(stringResource(R.string.cancel),color = colorResource(R.color.white))
                }
            },
            text = {
                Text(stringResource(R.string.delete_visit_question))
            },
        )
    }

}



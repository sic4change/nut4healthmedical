package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.create

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.os.LocaleListCompat
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Complication
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Symtom
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Treatment
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.VisitState
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun VisitItemCreateScreen(visitState: VisitState, loading: Boolean = false,
                          onCreateVisit: (Double, Double, Double, String, String, Boolean, Boolean,
                                          treatments: List<Treatment>, complications: List<Complication>,
                                          String) -> Unit,
                          onChangeWeightOrHeight: (String, String) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator()
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                Header(
                    visitState = visitState,
                    onCreateVisit = onCreateVisit,
                    onChangeWeightOrHeight = onChangeWeightOrHeight
                )
            }
            /*item.references.forEach {
                val (icon, @StringRes stringRes) = it.type.createUiData()
                section(icon, stringRes, it.references)
            }*/
        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ResourceAsColor")
@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@ExperimentalCoilApi
@Composable
private fun Header(visitState: VisitState,
                   onCreateVisit: (
                       Double, Double, Double, String, String, Boolean, Boolean,
                       treatments: List<Treatment>, complications: List<Complication>,
                       String,
                   ) -> Unit,
                   onChangeWeightOrHeight: (String, String) -> Unit) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.create_visit),
            color = colorResource(R.color.colorPrimary),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = visitState.height.value.toString(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            onValueChange = {
                visitState.formatHeightValue(it)
                onChangeWeightOrHeight(visitState.height.value.filter { !it.isWhitespace() },
                    visitState.weight.value.filter { !it.isWhitespace() })
            },
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
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            onValueChange = {
                visitState.formatWeightValue(it)
                onChangeWeightOrHeight(visitState.height.value.filter { !it.isWhitespace() },
                    visitState.weight.value.filter { !it.isWhitespace() })
            },
            textStyle = MaterialTheme.typography.h5,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.SpaceBar, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.weight), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateString = simpleDateFormat.format(visitState.childDateMillis.value)

        val monthsBetween = ChronoUnit.MONTHS.between(
                YearMonth.from(LocalDate.parse(dateString)), YearMonth.from(LocalDate.now())
        )

        AnimatedVisibility(visible = ((visitState.armCircunference.value != 30.0) && (monthsBetween >= 6 && monthsBetween <= 60))) {
            if (visitState.armCircunference.value < 11.5) {
                TextField(value = visitState.armCircunference.value.toString(),
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

        AnimatedVisibility(visible = (monthsBetween >= 6 && monthsBetween <= 60)) {
            AndroidView(
                factory = {
                    val view = LayoutInflater.from(it)
                        .inflate(R.layout.muac_view, null, false)
                    view
                },
                update  = {view ->
                    val ruler = view.findViewById<org.sic4change.nut4healthcentrotratamiento.ui.commons.SimpleRulerViewer>(R.id.ruler)
                    val rulerBackground = view.findViewById<View>(R.id.rulerBackground)
                    val tvCm = view.findViewById<TextView>(R.id.tvCm)
                    val df = DecimalFormat("#.0")
                    ruler.setOnValueChangeListener { view, position, value ->
                        tvCm.text = df.format(value).toString() + " cm"
                        visitState.armCircunference.value = df.format(value).replace(",", ".").toDouble()

                        if (value < 11.5
                                || (visitState.selectedEdema.value.isNotEmpty() && visitState.selectedEdema.value != "No")
                                || (visitState.complications.value.any{it.selected})
                        ) {
                            if (value < 11.5) {
                                rulerBackground.setBackgroundResource(R.color.error)
                            }
                            else if (value in 11.5..12.5) {
                                rulerBackground.setBackgroundResource(R.color.orange)
                            }
                            else {
                                rulerBackground.setBackgroundResource(R.color.colorAccent)
                            }
                            tvCm.setTextColor(R.color.error)
                            visitState.status.value = "Aguda Severa"
                        } else if (value in 11.5..12.5) {
                            rulerBackground.setBackgroundResource(R.color.orange)
                            tvCm.setTextColor(R.color.orange)
                            if (visitState.imc.value.equals(-1.5) || visitState.imc.value.equals(80.0) || visitState.imc.value.equals(-1.0) || visitState.imc.value.equals(85.0)
                                || visitState.imc.value.equals(0.0) || visitState.imc.value.equals(100.0)) {
                                visitState.status.value = "Aguda Moderada"
                            }
                        } else {
                            rulerBackground.setBackgroundResource(R.color.colorAccent)
                            tvCm.setTextColor(R.color.colorAccent)
                            if (visitState.imc.value.equals(0.0) || visitState.imc.value.equals(100.0)) {
                                visitState.status.value = "Normopeso"
                            } else if (visitState.imc.value.equals(-1.0) || visitState.imc.value.equals(85.0)) {
                                visitState.status.value = "Peso Objetivo"
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
            )
        }

        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            expanded = visitState.expandedEdema.value,
            onExpandedChange = {
                visitState.expandedEdema.value = !visitState.expandedEdema.value
            }
        ) {
            TextField(
                readOnly = true,
                value = visitState.selectedEdema.value,
                onValueChange = {
                    visitState.selectedEdema.value = it
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = visitState.expandedEdema.value
                    )
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
                    Icon(Icons.Filled.EggAlt, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                label = { Text(stringResource(R.string.edema), color = colorResource(R.color.disabled_color)) }
            )
            ExposedDropdownMenu(
                expanded = visitState.expandedEdema.value,
                onDismissRequest = {
                    visitState.expandedEdema.value = false
                }
            ) {
                stringArrayResource(id = R.array.edemaOptions).forEach { selectedEdema ->
                    DropdownMenuItem(
                        onClick = {
                            visitState.selectedEdema.value = selectedEdema
                            visitState.expandedEdema.value = false
                        }
                    ) {
                        Text(text = selectedEdema, color = colorResource(R.color.colorPrimary))
                    }
                }
            }
        }

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
                        .padding(16.dp, 0.dp),)

                visitState.complications.value.forEach { complication ->
                    ItemListComplications(complication = complication, checked = complication.selected) {
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

            if (visitState.selectedEdema.value == stringArrayResource(R.array.edemaOptions) [0]) {
                statusFormated = stringResource(R.string.normopeso)
            } else if (visitState.selectedEdema.value == stringArrayResource(R.array.edemaOptions) [1]
                || visitState.selectedEdema.value == stringArrayResource(R.array.edemaOptions) [2]
                || visitState.selectedEdema.value == stringArrayResource(R.array.edemaOptions) [3]) {
                statusFormated = stringResource(R.string.aguda_severa)
            }

            if (visitState.isOneComplicationSelected()) {
                statusFormated = stringResource(R.string.aguda_severa)
            }

            visitState.status.value = statusFormated

            if (visitState.status.value == stringResource(R.string.normopeso)) {
                TextField(value = visitState.status.value,
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
                        ExposedDropdownMenuBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 0.dp),
                            expanded = visitState.expandedRespiration.value,
                            onExpandedChange = {
                                visitState.expandedRespiration.value = !visitState.expandedRespiration.value
                            }
                        ) {
                            TextField(
                                readOnly = true,
                                value = visitState.selectedRespiration.value,
                                onValueChange = {
                                    visitState.selectedRespiration.value = it
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = visitState.expandedRespiration.value
                                    )
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
                                    Icon(Icons.Filled.Air, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                                label = { Text(stringResource(R.string.respiration), color = colorResource(R.color.disabled_color)) }
                            )
                            ExposedDropdownMenu(
                                expanded = visitState.expandedRespiration.value,
                                onDismissRequest = {
                                    visitState.expandedRespiration.value = false
                                }
                            ) {
                                stringArrayResource(id = R.array.respirationOptions).forEach { selectedRespiration ->
                                    DropdownMenuItem(
                                        onClick = {
                                            visitState.selectedRespiration.value = selectedRespiration
                                            visitState.expandedRespiration.value = false
                                        }
                                    ) {
                                        Text(text = selectedRespiration, color = colorResource(R.color.colorPrimary))
                                    }
                                }
                            }
                        }
                    }


                    AnimatedVisibility(visitState.status.value == stringResource(R.string.aguda_severa)) {
                        ExposedDropdownMenuBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 0.dp),
                            expanded = visitState.expandedApetit.value,
                            onExpandedChange = {
                                visitState.expandedApetit.value = !visitState.expandedApetit.value
                            }
                        ) {
                            TextField(
                                readOnly = true,
                                value = visitState.selectedApetit.value,
                                onValueChange = {
                                    visitState.selectedApetit.value = it
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = visitState.expandedApetit.value
                                    )
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
                                    Icon(Icons.Filled.BreakfastDining, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                                label = { Text(stringResource(R.string.apetit), color = colorResource(R.color.disabled_color)) }
                            )
                            ExposedDropdownMenu(
                                expanded = visitState.expandedApetit.value,
                                onDismissRequest = {
                                    visitState.expandedApetit.value = false
                                }
                            ) {
                                stringArrayResource(id = R.array.apetitOptions).forEach { selectedRespiration ->
                                    DropdownMenuItem(
                                        onClick = {
                                            visitState.selectedApetit.value = selectedRespiration
                                            visitState.expandedApetit.value = false
                                        }
                                    ) {
                                        Text(text = selectedRespiration, color = colorResource(R.color.colorPrimary))
                                    }
                                }
                            }
                        }
                    }

                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        expanded = visitState.expandedInfecction.value,
                        onExpandedChange = {
                            visitState.expandedInfecction.value = !visitState.expandedInfecction.value
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = visitState.selectedInfection.value,
                            onValueChange = {
                                visitState.selectedInfection.value = it
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = visitState.expandedInfecction.value
                                )
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
                                Icon(Icons.Filled.RemoveRedEye, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                            label = { Text(stringResource(R.string.infection), color = colorResource(R.color.disabled_color)) }
                        )
                        ExposedDropdownMenu(
                            expanded = visitState.expandedInfecction.value,
                            onDismissRequest = {
                                visitState.expandedInfecction.value = false
                            }
                        ) {
                            stringArrayResource(id = R.array.yesnooptions).forEach { selectedInfection ->
                                DropdownMenuItem(
                                    onClick = {
                                        visitState.selectedInfection.value = selectedInfection
                                        visitState.expandedInfecction.value = false
                                    }
                                ) {
                                    Text(text = selectedInfection, color = colorResource(R.color.colorPrimary))
                                }
                            }
                        }
                    }

                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        expanded = visitState.expandedEyes.value,
                        onExpandedChange = {
                            visitState.expandedEyes.value = !visitState.expandedEyes.value
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = visitState.selectedEyes.value,
                            onValueChange = {
                                visitState.selectedEyes.value = it
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = visitState.expandedEyes.value
                                )
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
                                Icon(Icons.Filled.RemoveRedEye, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                            label = { Text(stringResource(R.string.eyes), color = colorResource(R.color.disabled_color)) }
                        )
                        ExposedDropdownMenu(
                            expanded = visitState.expandedEyes.value,
                            onDismissRequest = {
                                visitState.expandedEyes.value = false
                            }
                        ) {
                            stringArrayResource(id = R.array.yesnooptions).forEach { selectedInfection ->
                                DropdownMenuItem(
                                    onClick = {
                                        visitState.selectedEyes.value = selectedInfection
                                        visitState.expandedEyes.value = false
                                    }
                                ) {
                                    Text(text = selectedInfection, color = colorResource(R.color.colorPrimary))
                                }
                            }
                        }
                    }

                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        expanded = visitState.expandedDeshidratation.value,
                        onExpandedChange = {
                            visitState.expandedDeshidratation.value = !visitState.expandedDeshidratation.value
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = visitState.selectedDeshidratation.value,
                            onValueChange = {
                                visitState.selectedDeshidratation.value = it
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = visitState.expandedDeshidratation.value
                                )
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
                                Icon(Icons.Filled.WaterDrop, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                            label = { Text(stringResource(R.string.deshidratation), color = colorResource(R.color.disabled_color)) }
                        )
                        ExposedDropdownMenu(
                            expanded = visitState.expandedDeshidratation.value,
                            onDismissRequest = {
                                visitState.expandedDeshidratation.value = false
                            }
                        ) {
                            stringArrayResource(id = R.array.yesnooptions).forEach { selectedInfection ->
                                DropdownMenuItem(
                                    onClick = {
                                        visitState.selectedDeshidratation.value = selectedInfection
                                        visitState.expandedDeshidratation.value = false
                                    }
                                ) {
                                    Text(text = selectedInfection, color = colorResource(R.color.colorPrimary))
                                }
                            }
                        }
                    }

                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        expanded = visitState.expandedVomitos.value,
                        onExpandedChange = {
                            visitState.expandedVomitos.value = !visitState.expandedVomitos.value
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = visitState.selectedVomitos.value,
                            onValueChange = {
                                visitState.selectedVomitos.value = it
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = visitState.expandedVomitos.value
                                )
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
                                Icon(Icons.Filled.PersonalInjury, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                            label = { Text(stringResource(R.string.vomits), color = colorResource(R.color.disabled_color)) }
                        )
                        ExposedDropdownMenu(
                            expanded = visitState.expandedVomitos.value,
                            onDismissRequest = {
                                visitState.expandedVomitos.value = false
                            }
                        ) {
                            stringArrayResource(id = R.array.frecuencyOptions).forEach { selectedInfection ->
                                DropdownMenuItem(
                                    onClick = {
                                        visitState.selectedVomitos.value = selectedInfection
                                        visitState.expandedVomitos.value = false
                                    }
                                ) {
                                    Text(text = selectedInfection, color = colorResource(R.color.colorPrimary))
                                }
                            }
                        }
                    }

                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        expanded = visitState.expandedDiarrea.value,
                        onExpandedChange = {
                            visitState.expandedDiarrea.value = !visitState.expandedDiarrea.value
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = visitState.selectedDiarrea.value,
                            onValueChange = {
                                visitState.selectedDiarrea.value = it
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = visitState.expandedDiarrea.value
                                )
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
                                Icon(Icons.Filled.PersonalInjury, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                            label = { Text(stringResource(R.string.diarrea), color = colorResource(R.color.disabled_color)) }
                        )
                        ExposedDropdownMenu(
                            expanded = visitState.expandedDiarrea.value,
                            onDismissRequest = {
                                visitState.expandedDiarrea.value = false
                            }
                        ) {
                            stringArrayResource(id = R.array.frecuencyOptions).forEach { selectedInfection ->
                                DropdownMenuItem(
                                    onClick = {
                                        visitState.selectedDiarrea.value = selectedInfection
                                        visitState.expandedDiarrea.value = false
                                    }
                                ) {
                                    Text(text = selectedInfection, color = colorResource(R.color.colorPrimary))
                                }
                            }
                        }
                    }

                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        expanded = visitState.expandedFiebre.value,
                        onExpandedChange = {
                            visitState.expandedFiebre.value = !visitState.expandedFiebre.value
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = visitState.selectedFiebre.value,
                            onValueChange = {
                                visitState.selectedFiebre.value = it
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = visitState.expandedFiebre.value
                                )
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
                                Icon(Icons.Filled.PersonalInjury, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                            label = { Text(stringResource(R.string.fiebre), color = colorResource(R.color.disabled_color)) }
                        )
                        ExposedDropdownMenu(
                            expanded = visitState.expandedFiebre.value,
                            onDismissRequest = {
                                visitState.expandedFiebre.value = false
                            }
                        ) {
                            stringArrayResource(id = R.array.frecuencyOptions).forEach { selectedInfection ->
                                DropdownMenuItem(
                                    onClick = {
                                        visitState.selectedFiebre.value = selectedInfection
                                        visitState.expandedFiebre.value = false
                                    }
                                ) {
                                    Text(text = selectedInfection, color = colorResource(R.color.colorPrimary))
                                }
                            }
                        }
                    }

                    AnimatedVisibility(visitState.status.value == stringResource(R.string.aguda_severa)) {
                        ExposedDropdownMenuBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 0.dp),
                            expanded = visitState.expandedTos.value,
                            onExpandedChange = {
                                visitState.expandedTos.value = !visitState.expandedTos.value
                            }
                        ) {
                            TextField(
                                readOnly = true,
                                value = visitState.selectedTos.value,
                                onValueChange = {
                                    visitState.selectedTos.value = it
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = visitState.expandedTos.value
                                    )
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
                                    Icon(Icons.Filled.PersonalInjury, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                                label = { Text(stringResource(R.string.tos), color = colorResource(R.color.disabled_color)) }
                            )
                            ExposedDropdownMenu(
                                expanded = visitState.expandedTos.value,
                                onDismissRequest = {
                                    visitState.expandedTos.value = false
                                }
                            ) {
                                stringArrayResource(id = R.array.yesnooptions).forEach { selectedInfection ->
                                    DropdownMenuItem(
                                        onClick = {
                                            visitState.selectedTos.value = selectedInfection
                                            visitState.expandedTos.value = false
                                        }
                                    ) {
                                        Text(text = selectedInfection, color = colorResource(R.color.colorPrimary))
                                    }
                                }
                            }
                        }
                    }

                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        expanded = visitState.expandedTemperature.value,
                        onExpandedChange = {
                            visitState.expandedTemperature.value = !visitState.expandedTemperature.value
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = visitState.selectedTemperature.value,
                            onValueChange = {
                                visitState.selectedTemperature.value = it
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = visitState.expandedTemperature.value
                                )
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
                                Icon(Icons.Filled.Thermostat, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                            label = { Text(stringResource(R.string.temperature), color = colorResource(R.color.disabled_color)) }
                        )
                        ExposedDropdownMenu(
                            expanded = visitState.expandedTemperature.value,
                            onDismissRequest = {
                                visitState.expandedTemperature.value = false
                            }
                        ) {
                            stringArrayResource(id = R.array.temperatureoptions).forEach { selectedInfection ->
                                DropdownMenuItem(
                                    onClick = {
                                        visitState.selectedTemperature.value = selectedInfection
                                        visitState.expandedTemperature.value = false
                                    }
                                ) {
                                    Text(text = selectedInfection, color = colorResource(R.color.colorPrimary))
                                }
                            }
                        }
                    }

                }
            }
        }

        AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                && visitState.status.value == stringResource(R.string.aguda_moderada)) {
            Spacer(modifier = Modifier.height(16.dp))
        }

        AnimatedVisibility(visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                && visitState.status.value == stringResource(R.string.aguda_moderada)
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
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(0.dp, 16.dp)
                ) {

                    Spacer(modifier = Modifier.height(16.dp))

                    CheckNUT4H(text = stringResource(id = R.string.vitamineAVaccinated), visitState.vitamineAVaccinated.value) {
                        visitState.vitamineAVaccinated.value = it
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



        /*Spacer(modifier = Modifier.height(16.dp))

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
                Text(text = stringResource(R.string.treatments), color = colorResource(R.color.disabled_color),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),)

                visitState.treatments.value.forEach { treatment ->
                    ItemListTreatments(treatment = treatment, checked = treatment.selected,  {
                        var treatmentsToUpdate : MutableList<Treatment> = mutableListOf()
                        visitState.treatments.value.forEach { item ->
                            if (item.id == treatment.id) {
                                item.selected = it
                            }
                            treatmentsToUpdate.add(item)
                        }
                        visitState.treatments.value = mutableListOf()
                        visitState.treatments.value.addAll(treatmentsToUpdate)
                    })
                }
            }
        }*/

        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = visitState.observations.value,
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

        AnimatedVisibility(visible = (visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty())) {
            Button(
                enabled = !visitState.createdVisit.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                onClick = {
                    visitState.createdVisit.value = true
                    onCreateVisit(visitState.height.value.filter { !it.isWhitespace() }.toDouble(),
                        visitState.weight.value.filter { !it.isWhitespace() }.toDouble(),
                        visitState.armCircunference.value, visitState.status.value, visitState.selectedEdema.value,
                        visitState.measlesVaccinated.value, visitState.vitamineAVaccinated.value,
                        visitState.treatments.value, visitState.complications.value,
                        visitState.observations.value,)

                },
            ) {
                Text(stringResource(R.string.save), color = colorResource(R.color.white), style = MaterialTheme.typography.h5)
            }
        }

        AnimatedVisibility(visible = (visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty() )) {
            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}

@Composable
fun CheckNUT4H(text: String, checked: Boolean, onCheckedChange : (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp)
            .clickable(
                onClick = {
                    onCheckedChange(!checked)
                }
            ),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(colorResource(R.color.colorPrimaryDark)),
        )

        Text(
            color = colorResource(R.color.colorPrimary),
            text = text,
            style = MaterialTheme.typography.body1,
        )


    }
}

@Composable
fun ItemListComplications(complication: Complication, checked: Boolean, onCheckedChange : (Boolean) -> Unit) {

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
            .padding(16.dp, 0.dp)
            .clickable(
                onClick = {
                    onCheckedChange(!checked)
                }),
        verticalAlignment = Alignment.CenterVertically,

        ) {

        Checkbox(
            checked = checked,
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

@Composable
fun ItemListSymtoms(symtom: Symtom, checked: Boolean, onCheckedChangeSymtom : (Boolean) -> Unit) {

    val language = LocaleListCompat.getDefault()[0]!!.toLanguageTag()
    var symtomTag = ""
    if (language.contains("es-")) {
        symtomTag = symtom.name
    } else if(language.contains("en-")) {
        symtomTag = symtom.name_en
    } else {
        symtomTag = symtom.name_fr
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp)
            .clickable(
                onClick = {
                    onCheckedChangeSymtom(!checked)
                }),
        verticalAlignment = Alignment.CenterVertically,

        ) {

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChangeSymtom,
            colors = CheckboxDefaults.colors(colorResource(R.color.colorPrimaryDark)),
        )

        Text(
            color = colorResource(R.color.colorPrimary),
            text = symtomTag,
            style = MaterialTheme.typography.body1,
        )

    }

}


@Composable
fun ItemListTreatments(treatment: Treatment, checked: Boolean,  onCheckedChangeTreatment : (Boolean) -> Unit) {

    val language = LocaleListCompat.getDefault()[0]!!.toLanguageTag()
    var treatmentTag = ""
    if (language.contains("es-")) {
        treatmentTag = treatment.name
    } else if(language.contains("en-")) {
        treatmentTag = treatment.name_en
    } else {
        treatmentTag = treatment.name_fr
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp)
            .clickable(
                onClick = {
                    onCheckedChangeTreatment(!checked)
                }
            ),
        verticalAlignment = Alignment.CenterVertically,

        ) {

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChangeTreatment,
            colors = CheckboxDefaults.colors(colorResource(R.color.colorPrimaryDark)),
        )

        Text(
            color = colorResource(R.color.colorPrimary),
            text = treatmentTag,
            style = MaterialTheme.typography.body1,
        )
    }

}



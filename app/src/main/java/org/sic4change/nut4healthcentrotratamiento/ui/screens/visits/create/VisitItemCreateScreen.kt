package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.create

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.VisitState
import java.text.DecimalFormat

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun VisitItemCreateScreen(visitState: VisitState, loading: Boolean = false,
                         onCreateVisit: (Double, Double, Double, String, Boolean, Boolean, String) -> Unit,
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

@SuppressLint("ResourceAsColor")
@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@ExperimentalCoilApi
@Composable
private fun Header(visitState: VisitState,
                   onCreateVisit: (Double, Double, Double, String, Boolean, Boolean, String) -> Unit,
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
                visitState.height.value = it
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
                visitState.weight.value = it
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

        AnimatedVisibility(visible = (visitState.armCircunference.value != 0.0)) {
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

        AnimatedVisibility(visible = (visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty() )) {
            Spacer(modifier = Modifier.height(16.dp))
        }

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

                   if (value < 11.5) {
                       rulerBackground.setBackgroundResource(R.color.error)
                       tvCm.setTextColor(R.color.error)
                       visitState.status.value = "Aguda Severa"
                   } else if (value >= 11.5 && value <= 12.5) {
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

        AnimatedVisibility(visible = (visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty() )) {
            Spacer(modifier = Modifier.height(16.dp))
        }


        CheckNUT4H(text = stringResource(id = R.string.measlesVaccinated), visitState.measlesVaccinated.value) {
            visitState.measlesVaccinated.value = it
        }

        Spacer(modifier = Modifier.height(16.dp))

        CheckNUT4H(text = stringResource(id = R.string.vitamineAVaccinated), visitState.vitamineAVaccinated.value) {
            visitState.vitamineAVaccinated.value = it
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(R.string.symtoms), color = colorResource(R.color.colorPrimary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),)

        visitState.symtoms.value.forEach {
            var index = 0;
            ItemList(name = it.name_fr, MutableList(visitState.symtoms.value.size) { false }, index)
            index++
        }

        Text(text = stringResource(R.string.treatments), color = colorResource(R.color.colorPrimary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),)

        visitState.treatments.value.forEach {
            var index = 0;
            ItemList(name = it.name_fr, MutableList(visitState.treatments.value.size) { false }, index)
            index++
        }

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

        AnimatedVisibility(visible = (visitState.weight.value.isNotEmpty() && visitState.height.value.isNotEmpty()
                && visitState.armCircunference.value != 0.0 )) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                onClick = {
                    onCreateVisit(visitState.height.value.filter { !it.isWhitespace() }.toDouble(),
                        visitState.weight.value.filter { !it.isWhitespace() }.toDouble(),
                        visitState.armCircunference.value, visitState.status.value,
                        visitState.measlesVaccinated.value, visitState.vitamineAVaccinated.value,
                        visitState.observations.value)

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
        modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp)
            .clickable(
                onClick = {
                    onCheckedChange(!checked)
                }
            ),
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Text(
            color = colorResource(R.color.colorPrimary),
            text = text,
            style = MaterialTheme.typography.body1,
        )

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(colorResource(R.color.colorPrimaryDark)),
        )
    }
}


@Composable
fun ItemList(name: String, completedList: MutableList<Boolean>, index: Int) {
    var isSelected by rememberSaveable { mutableStateOf(completedList[index]) }

    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,

        ) {

        Text(
            color = colorResource(R.color.colorPrimary),
            text = name,
            style = MaterialTheme.typography.body1,
        )

        Checkbox(
            checked = isSelected,
            onCheckedChange = {
                isSelected = !isSelected
                completedList[index] = !isSelected
            },
            colors = CheckboxDefaults.colors(colorResource(R.color.colorPrimaryDark)),
        )
    }
    
}



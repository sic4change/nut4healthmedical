package org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.create

import DatePickerView
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.ui.commons.CustomDatePickerDialog
import org.sic4change.nut4healthcentrotratamiento.ui.commons.Gender
import org.sic4change.nut4healthcentrotratamiento.ui.commons.GenderToggleButton
import org.sic4change.nut4healthcentrotratamiento.ui.commons.formatDateToString
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.ChildState
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ChildItemCreateScreen(childState: ChildState, loading: Boolean = false,
onCreateChild: (String, String, Date, Int, String, String, String) -> Unit) {
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
                Header(childState = childState, onCreateChild = onCreateChild)
            }
            /*item.references.forEach {
                val (icon, @StringRes stringRes) = it.type.createUiData()
                section(icon, stringRes, it.references)
            }*/
        }

    }

}

@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@ExperimentalCoilApi
@Composable
private fun Header(childState: ChildState,
                   onCreateChild: (String, String, Date, Int, String, String, String) -> Unit) {

    val SEXS = listOf(
        stringResource(R.string.female), stringResource(R.string.male)
    )

    val etnicians = listOf(
        stringResource(R.string.pulaar), stringResource(R.string.wolof), stringResource(
            R.string.beydan), stringResource(R.string.haratin), stringResource(R.string.soninke),
        stringResource(R.string.autre))

    val brotherOptions = listOf(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21)

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.create_child),
            color = colorResource(R.color.colorPrimary),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = childState.name.value,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            onValueChange = {childState.name.value = it},
            textStyle = MaterialTheme.typography.h5,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Person, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.name), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = childState.surnames.value,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            onValueChange = {childState.surnames.value = it},
            textStyle = MaterialTheme.typography.h5,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Person, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.surnames), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = SimpleDateFormat("dd/MM/yyyy").format(childState.birthday.value),
            enabled = false,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            onValueChange = {childState.birthday.value = SimpleDateFormat("dd-MM-yyyy").parse(it)},
            textStyle = MaterialTheme.typography.h5,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),
            modifier = Modifier
                .clickable {
                    childState.showDateDialog.value = true
                }
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Cake, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.birthdate), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            expanded = childState.expandedBrothers.value,
            onExpandedChange = {
                childState.expandedBrothers.value = !childState.expandedBrothers.value
            }
        ) {
            TextField(
                readOnly = true,
                value = childState.selectedOptionBrothers.value.toString(),
                onValueChange = { childState.selectedOptionBrothers.value = it.toInt()
                    childState.brothers.value = it.toInt() },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = childState.expandedBrothers.value
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
                    Icon(painterResource(R.mipmap.ic_brothers), null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                label = { Text(stringResource(R.string.child_brothers), color = colorResource(R.color.disabled_color)) }
            )
            ExposedDropdownMenu(
                expanded = childState.expandedBrothers.value,
                onDismissRequest = {
                    childState.expandedBrothers.value = false
                }
            ) {
                brotherOptions.forEach { selectionOption2 ->
                    DropdownMenuItem(
                        onClick = {
                            childState.selectedOptionBrothers.value = selectionOption2
                            childState.expandedBrothers.value = false
                        }
                    ) {
                        Text(text = selectionOption2.toString(), color = colorResource(R.color.colorPrimary))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            expanded = childState.expandedEtnician.value,
            onExpandedChange = {
                childState.expandedEtnician.value = !childState.expandedEtnician.value
            }
        ) {
            TextField(
                readOnly = true,
                value = childState.selectedOptionEtnician.value,
                onValueChange = { childState.selectedOptionEtnician.value = it
                    childState.etnician.value = it },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = childState.expandedEtnician.value
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
                    Icon(Icons.Filled.Face, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                label = { Text(stringResource(R.string.etnician), color = colorResource(R.color.disabled_color)) }
            )
            ExposedDropdownMenu(
                expanded = childState.expandedEtnician.value,
                onDismissRequest = {
                    childState.expandedEtnician.value = false
                }
            ) {
                etnicians.forEach { selectionOption2 ->
                    DropdownMenuItem(
                        onClick = {
                            childState.selectedOptionEtnician.value = selectionOption2
                            childState.expandedEtnician.value = false
                        }
                    ) {
                        Text(text = selectionOption2, color = colorResource(R.color.colorPrimary))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(R.string.sex), color = colorResource(R.color.disabled_color))
        }
        Box(modifier = Modifier.fillMaxSize() .fillMaxWidth()
            .padding(16.dp, 0.dp),) {
            GenderToggleButton(
                defaultGender = if (childState.selectedOptionSex.value == SEXS[1]) Gender.MALE else Gender.FEMALE,
                enabled = true,
                onGenderSelected = {
                    val selectionOption = if (it == Gender.FEMALE) SEXS[0] else SEXS[1]
                    childState.selectedOptionSex.value = selectionOption
                    childState.expandedSex.value = false
                })
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = childState.observations.value,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            onValueChange = {childState.observations.value = it},
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Edit, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.observations), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(visible = childState.showDateDialog.value) {
            CustomDatePickerDialog(
                value = formatDateToString(childState.birthday.value, "dd/MM/yyyy"),
                onDismissRequest = {
                    childState.showDateDialog.value = false
                    var date = SimpleDateFormat("dd-MM-yyyy").parse(it)
                    childState.birthday.value = date
                },
            )
        }


        AnimatedVisibility(visible = (childState.name.value.isNotEmpty() &&
                childState.surnames.value.isNotEmpty())) {
            Button(
                enabled = !childState.createdChild.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                onClick = {
                    childState.createdChild.value = true
                    onCreateChild(childState.name.value, childState.surnames.value,
                        childState.birthday.value, childState.selectedOptionBrothers.value,
                        childState.selectedOptionEtnician.value, childState.selectedOptionSex.value,
                        childState.observations.value)

                },
            ) {
                Text(stringResource(R.string.save), color = colorResource(R.color.white), style = MaterialTheme.typography.h5)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}



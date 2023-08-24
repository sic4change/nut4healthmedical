package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.edit

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.ui.commons.CustomDatePickerDialog
import org.sic4change.nut4healthcentrotratamiento.ui.commons.formatDateToString
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.TutorState
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun TutorItemEditScreen(tutorState: TutorState, loading: Boolean = false,
                        onEditTutor: (String, String, String, String, String, Date, String, String,
                                      String, String, String, String, String, String) -> Unit) {
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
                    tutorState = tutorState,
                    onEditTutor = onEditTutor)
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
private fun Header(tutorState: TutorState,
                   onEditTutor: (String, String, String, String, String, Date, String, String, String,
                                 String, String, String, String, String) -> Unit) {

    val SEXS = listOf(
        stringResource(R.string.female), stringResource(R.string.male)
    )

    val WOMANSTATUS = listOf(
        stringResource(R.string.pregnant), stringResource(R.string.infant), stringResource(
            R.string.pregnant_and_infant), stringResource(R.string.nothing)
    )

    val MALE_RELATIONS = listOf(
        stringResource(R.string.father), stringResource(R.string.grandfather), stringResource(
            R.string.brother), stringResource(R.string.uncle), stringResource(R.string.cousin),
        stringResource(R.string.other)
    )

    val ETNICIANS = listOf(
        stringResource(R.string.pulaar), stringResource(R.string.wolof), stringResource(
            R.string.beydan), stringResource(R.string.haratin), stringResource(R.string.soninke),
        stringResource(R.string.autre)
    )

    val CHILD_MINOR = listOf(stringResource(R.string.child_minor), stringResource(R.string.no_child_minor))

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.editar_tutor),
            color = colorResource(R.color.colorPrimary),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorState.name.value,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            onValueChange = {tutorState.name.value = it},
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Person, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.name), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorState.surnames.value,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            onValueChange = {tutorState.surnames.value = it},
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Person, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.surnames), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorState.address.value,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            onValueChange = {tutorState.address.value = it},
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Place, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.address), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorState.phone.value,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            enabled = false,
            onValueChange = {tutorState.phone.value = it},
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Phone, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.phone), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = SimpleDateFormat("dd/MM/yyyy").format(tutorState.birthday.value),
            enabled = false,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            onValueChange = {tutorState.birthday.value = SimpleDateFormat("dd-MM-yyyy").parse(it)},
            textStyle = MaterialTheme.typography.h5,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),
            modifier = Modifier
                .clickable {
                    tutorState.showDateDialog.value = true
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
            expanded = tutorState.expandedEtnician.value,
            onExpandedChange = {
                tutorState.expandedEtnician.value = !tutorState.expandedEtnician.value
            }
        ) {
            TextField(
                readOnly = true,
                value = tutorState.selectedOptionEtnician.value,
                onValueChange = { tutorState.selectedOptionEtnician.value = it
                    tutorState.etnician.value = it },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = tutorState.expandedEtnician.value
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
                    Icon(Icons.Filled.Face, null, tint = colorResource(R.color.colorPrimary),  )},
                label = { Text(stringResource(R.string.etnician), color = colorResource(R.color.disabled_color)) }
            )
            ExposedDropdownMenu(
                expanded = tutorState.expandedEtnician.value,
                onDismissRequest = {
                    tutorState.expandedEtnician.value = false
                }
            ) {
                ETNICIANS.forEach { selectionOption2 ->
                    DropdownMenuItem(
                        onClick = {
                            tutorState.selectedOptionEtnician.value = selectionOption2
                            tutorState.expandedEtnician.value = false
                        }
                    ) {
                        Text(text = selectionOption2, color = colorResource(R.color.colorPrimary))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            expanded = tutorState.expandedSex.value,
            onExpandedChange = {
                tutorState.expandedSex.value = !tutorState.expandedSex.value
            }
        ) {
            TextField(
                readOnly = true,
                value = tutorState.selectedOptionSex.value,
                onValueChange = { tutorState.selectedOptionSex.value = it
                    tutorState.sex.value = it },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = tutorState.expandedSex.value
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
                    if (tutorState.selectedOptionSex.value == SEXS[0]) {
                        Icon(
                            Icons.Filled.Female,
                            null,
                            tint = colorResource(R.color.colorPrimary),
                            modifier = Modifier.clickable { /* .. */ })
                    } else if (tutorState.selectedOptionSex.value == SEXS[1]) {
                        Icon(
                            Icons.Filled.Male,
                            null,
                            tint = colorResource(R.color.colorPrimary),
                            modifier = Modifier.clickable { /* .. */ })
                    } else {
                        Icon(
                            painterResource(R.mipmap.ic_sex_selection),
                            null,
                            tint = colorResource(R.color.colorPrimary),
                            modifier = Modifier.clickable { /* .. */ })
                    }
                },
                label = { Text(stringResource(R.string.sex), color = colorResource(R.color.disabled_color)) }
            )
            ExposedDropdownMenu(
                expanded = tutorState.expandedSex.value,
                onDismissRequest = {
                    tutorState.expandedSex.value = false
                }
            ) {
                SEXS.forEach { selectionOption2 ->
                    DropdownMenuItem(
                        onClick = {
                            tutorState.selectedOptionSex.value = selectionOption2
                            tutorState.expandedSex.value = false
                        }
                    ) {
                        Text(text = selectionOption2, color = colorResource(R.color.colorPrimary))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(visible = (tutorState.selectedOptionSex.value == stringResource(R.string.male))) {
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                expanded = tutorState.expandedMaleRelation.value,
                onExpandedChange = {
                    tutorState.expandedMaleRelation.value = !tutorState.expandedMaleRelation.value
                }
            ) {
                TextField(
                    readOnly = true,
                    value = tutorState.selectedOptionMaleRelations.value,
                    onValueChange = { tutorState.selectedOptionMaleRelations.value = it
                        tutorState.sex.value = it },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = tutorState.expandedMaleRelation.value
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
                        Icon(painterResource(R.mipmap.ic_relation), null, tint = colorResource(R.color.colorPrimary),  )},
                    label = { Text(stringResource(R.string.relation), color = colorResource(R.color.disabled_color)) }
                )
                ExposedDropdownMenu(
                    expanded = tutorState.expandedMaleRelation.value,
                    onDismissRequest = {
                        tutorState.expandedMaleRelation.value = false
                    }
                ) {
                    MALE_RELATIONS.forEach { selectionOption2 ->
                        DropdownMenuItem(
                            onClick = {
                                tutorState.selectedOptionMaleRelations.value = selectionOption2
                                tutorState.expandedMaleRelation.value = false
                            }
                        ) {
                            Text(text = selectionOption2, color = colorResource(R.color.colorPrimary))
                        }
                    }
                }
            }
        }

        AnimatedVisibility(visible = (tutorState.selectedOptionSex.value == stringResource(R.string.male))) {
            Spacer(modifier = Modifier.height(16.dp))
        }

        AnimatedVisibility(visible = (tutorState.selectedOptionSex.value == stringResource(R.string.female))) {
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                expanded = tutorState.expandedWomanStatus.value,
                onExpandedChange = {
                    tutorState.expandedWomanStatus.value = !tutorState.expandedWomanStatus.value
                }
            ) {
                TextField(
                    readOnly = true,
                    value = tutorState.selectedOptionWomanStatus.value,
                    onValueChange = { tutorState.selectedOptionWomanStatus.value = it
                        tutorState.womanStatus.value = it },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = tutorState.expandedWomanStatus.value
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
                        Icon(Icons.Filled.PregnantWoman, null, tint = colorResource(R.color.colorPrimary), )},
                    label = { Text(stringResource(R.string.status), color = colorResource(R.color.disabled_color)) }
                )
                ExposedDropdownMenu(
                    expanded = tutorState.expandedWomanStatus.value,
                    onDismissRequest = {
                        tutorState.expandedWomanStatus.value = false
                    }
                ) {
                    WOMANSTATUS.forEach { selectionOption2 ->
                        DropdownMenuItem(
                            onClick = {
                                tutorState.selectedOptionWomanStatus.value = selectionOption2
                                tutorState.expandedWomanStatus.value = false
                            }
                        ) {
                            Text(text = selectionOption2, color = colorResource(R.color.colorPrimary))
                        }
                    }
                }
            }
        }
        AnimatedVisibility(visible = (tutorState.selectedOptionSex.value == stringResource(R.string.female))) {
            Spacer(modifier = Modifier.height(16.dp))
        }
        AnimatedVisibility(visible = (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant) ||
                tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant_and_infant))) {
            TextField(value = tutorState.weeks.value.toString(),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.colorPrimary),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.colorAccent),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.colorAccent),
                    unfocusedIndicatorColor = colorResource(R.color.colorAccent),
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                onValueChange = {newText ->
                    try {
                        if (newText.all { it.isDigit() }) {
                            if (newText.isEmpty()) {
                                tutorState.weeks.value = newText
                            } else {
                                val number = newText.toIntOrNull()
                                if (number != null && number in 1..53) {
                                    tutorState.weeks.value = newText
                                }
                            }
                        }
                    } catch (e: Exception) { } },
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(Icons.Filled.ViewWeek, null, tint = colorResource(R.color.colorPrimary),  )},
                label = { Text(stringResource(R.string.weeks), color = colorResource(R.color.disabled_color)) }

            )

        }
        AnimatedVisibility(visible = (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant) ||
                tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant_and_infant))) {
            Spacer(modifier = Modifier.height(16.dp))
        }
        AnimatedVisibility(visible = (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant))) {
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                expanded = tutorState.expandedChildMinor.value,
                onExpandedChange = {
                    tutorState.expandedChildMinor.value = !tutorState.expandedChildMinor.value
                }
            ) {
                TextField(
                    readOnly = true,
                    value = tutorState.selectedOptionChildMinor.value,
                    onValueChange = { tutorState.selectedOptionChildMinor.value = it
                        tutorState.childMinor.value = it },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = tutorState.expandedChildMinor.value
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
                        Icon(Icons.Filled.ChildCare, null, tint = colorResource(R.color.colorPrimary),  )},
                    label = { Text(stringResource(R.string.child_minor_six_month), color = colorResource(R.color.disabled_color)) }
                )
                ExposedDropdownMenu(
                    expanded = tutorState.expandedChildMinor.value,
                    onDismissRequest = {
                        tutorState.expandedChildMinor.value = false
                    }
                ) {
                    CHILD_MINOR.forEach { selectionOption2 ->
                        DropdownMenuItem(
                            onClick = {
                                tutorState.selectedOptionChildMinor.value = selectionOption2
                                tutorState.expandedChildMinor.value = false
                            }
                        ) {
                            Text(text = selectionOption2, color = colorResource(R.color.colorPrimary))
                        }
                    }
                }
            }
        }
        AnimatedVisibility(visible = (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant))) {
            Spacer(modifier = Modifier.height(16.dp))
        }
        AnimatedVisibility(visible = (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.infant) ||
                tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant_and_infant))) {
            TextField(value = tutorState.babyAge.value.toString(),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.colorPrimary),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.colorAccent),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.colorAccent),
                    unfocusedIndicatorColor = colorResource(R.color.colorAccent),
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                onValueChange = { newText ->
                    try {
                        if (newText.all { it.isDigit() }) {
                            tutorState.babyAge.value = newText
                        }

                    } catch (e: Exception) { } },
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(Icons.Filled.ChildFriendly, null, tint = colorResource(R.color.colorPrimary),  )},
                label = { Text(stringResource(R.string.baby_age), color = colorResource(R.color.disabled_color)) }

            )

        }
        AnimatedVisibility(visible = (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.infant) ||
                tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant_and_infant))) {
            Spacer(modifier = Modifier.height(16.dp))
        }

        TextField(value = tutorState.observations.value,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            onValueChange = {tutorState.observations.value = it},
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Edit, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.observations), color = colorResource(R.color.disabled_color)) })

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(visible = tutorState.showDateDialog.value) {
            CustomDatePickerDialog(
                value = formatDateToString(tutorState.birthday.value, "dd/MM/yyyy"),
                onDismissRequest = {
                    tutorState.showDateDialog.value = false
                    tutorState.birthday.value = SimpleDateFormat("dd-MM-yyyy").parse(it)
                },
            )
        }

        AnimatedVisibility(visible = (tutorState.name.value.isNotEmpty() &&
                tutorState.surnames.value.isNotEmpty() &&
                tutorState.phone.value.isNotEmpty() &&
                tutorState.address.value.isNotEmpty())) {
            if (tutorState.selectedOptionSex.value == stringResource(R.string.male)){
                tutorState.clearWomanValues()
            } else {
                tutorState.clearManValues()
                if (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant)){
                    tutorState.clearWomanPregnantStatusValue()
                } else if (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.infant)) {
                    tutorState.clearWomanInfantStatusValue()
                } else if(tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant_and_infant)) {
                    tutorState.clearWomanPregnantAndInfantStatusValue()
                } else if (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.nothing)) {
                    tutorState.clearWomanOtherStatusValue()
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),

                onClick = {
                    tutorState.createdTutor.value = true
                    onEditTutor(tutorState.id.value, tutorState.name.value, tutorState.surnames.value,
                        tutorState.address.value, tutorState.phone.value, tutorState.birthday.value,
                        tutorState.selectedOptionEtnician.value, tutorState.selectedOptionSex.value,
                        tutorState.selectedOptionMaleRelations.value, tutorState.selectedOptionWomanStatus.value,
                        tutorState.weeks.value, tutorState.selectedOptionChildMinor.value,
                        tutorState.babyAge.value, tutorState.observations.value)
                },
            ) {
                Text(stringResource(R.string.save), color = colorResource(R.color.white), style = MaterialTheme.typography.h5)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}



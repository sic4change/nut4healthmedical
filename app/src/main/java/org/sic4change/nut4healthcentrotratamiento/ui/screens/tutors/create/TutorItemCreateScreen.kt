package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.create

import DatePickerView
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.annotation.ExperimentalCoilApi
import com.robertlevonyan.compose.buttontogglegroup.RowToggleButtonGroup
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.ui.commons.SimpleRulerViewer
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.TutorState
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun TutorItemCreateScreen(tutorState: TutorState, loading: Boolean = false,
onCreateTutor: (String, String, String, String, Date, String, String, String, String, String,
                String, Double, String, String, String) -> Unit) {
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
                    onCreateTutor = onCreateTutor)
            }
        }

    }

}

@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@ExperimentalCoilApi
@Composable
private fun Header(tutorState: TutorState,
                   onCreateTutor: (String, String, String, String, Date, String, String, String,
                                   String, String, String, Double, String, String, String) -> Unit) {

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
        Text(stringResource(R.string.create_tutor),
            color = colorResource(R.color.colorPrimary),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(4.dp, 0.dp)
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
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences),
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
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences),
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
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Place, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.address), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorState.phone.value,
            enabled = false,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.colorAccent),
            ),
            onValueChange = {tutorState.phone.value = it},
            textStyle = MaterialTheme.typography.h5,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Phone, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.phone), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        DatePickerView(
            showMonths = false,
            context = LocalContext.current,
            value = SimpleDateFormat("dd/MM/yyyy").format(tutorState.birthday.value),
            setValue = { tutorState.birthday.value = SimpleDateFormat("dd-MM-yyyy").parse(it)}
        )
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
                    Icon(Icons.Filled.Face, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
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
        Column(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(R.string.sex), color = colorResource(R.color.disabled_color))
        }
        Box(modifier = Modifier.fillMaxSize() .fillMaxWidth()
            .padding(16.dp, 0.dp),) {
            RowToggleButtonGroup(
                modifier = Modifier,
                buttonCount = 2,
                borderColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                selectedColor = colorResource(R.color.colorPrimary),
                unselectedColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                selectedContentColor = colorResource(R.color.white),
                unselectedContentColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                elevation = ButtonDefaults.elevation(0.dp), // elevation of toggle group buttons
                buttonIcons = arrayOf(
                    painterResource(id = R.drawable.female),
                    painterResource(id = R.drawable.male),
                ),

            ) { index ->
                val selectionOption = SEXS[index]
                tutorState.selectedOptionSex.value = selectionOption
                tutorState.expandedSex.value = false
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
                        Icon(Icons.Filled.EmojiPeople, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
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
                        Icon(Icons.Filled.PregnantWoman, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
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
            TextField(value = tutorState.weeks.value,
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
                    Icon(Icons.Filled.ViewWeek, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
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
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Filled.ChildCare, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
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
            TextField(value = tutorState.babyAge.value,
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
                onValueChange = { newText->
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
                    Icon(Icons.Filled.ChildFriendly, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
                label = { Text(stringResource(R.string.baby_age), color = colorResource(R.color.disabled_color)) }

            )

        }
        AnimatedVisibility(visible = (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.infant) ||
                tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant_and_infant))) {
            Spacer(modifier = Modifier.height(16.dp))
        }

        AnimatedVisibility(visible = (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.infant) ||
                tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant_and_infant )||
                tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant))) {
            if (tutorState.armCircunference.value < 18.0) {
                TextField(value = tutorState.armCircunference.value.toString() + " " + stringResource(R.string.aguda_severa),
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
            }  else if (tutorState.armCircunference.value >= 18.0 && tutorState.armCircunference.value < 21.0) {
                TextField(value = tutorState.armCircunference.value.toString() + " " + stringResource(R.string.aguda_moderada),
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
                TextField(value = tutorState.armCircunference.value.toString() + " " + stringResource(R.string.normopeso),
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

        AnimatedVisibility(visible = (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.infant) ||
                tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant_and_infant )||
                tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant))) {
            Spacer(modifier = Modifier.height(16.dp))
        }

        AnimatedVisibility(visible = (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.infant) ||
                tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant_and_infant )||
                tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant))) {
            AndroidView(
                factory = {
                    val view = LayoutInflater.from(it)
                        .inflate(R.layout.woman_muac_view, null, false)
                    view
                },
                update  = {view ->
                    val ruler = view.findViewById<SimpleRulerViewer>(R.id.ruler)
                    val rulerBackground = view.findViewById<View>(R.id.rulerBackground)
                    val tvCm = view.findViewById<TextView>(R.id.tvCm)
                    val df = DecimalFormat("#.0")
                    ruler.setOnValueChangeListener { view, position, value ->
                        tvCm.text = df.format(value).toString() + " cm"
                        tutorState.armCircunference.value = df.format(value).replace(",", ".").toDouble()
                        if (value < 18.0) {
                            rulerBackground.setBackgroundResource(R.color.error)
                            tvCm.setTextColor(R.color.error)
                            tutorState.status.value = "SAM"
                        } else if (value >= 18.0 && value < 21.0) {
                            rulerBackground.setBackgroundResource(R.color.orange)
                            tvCm.setTextColor(R.color.orange)
                            tutorState.status.value = "MAM"
                        } else {
                            rulerBackground.setBackgroundResource(R.color.colorAccent)
                            tvCm.setTextColor(R.color.colorAccent)
                            tutorState.status.value = "NW"
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
            )
        }

        AnimatedVisibility(visible = (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.infant) ||
                tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant_and_infant )||
                tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant))) {
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

        AnimatedVisibility(visible = (tutorState.name.value.isNotEmpty() &&
                tutorState.surnames.value.isNotEmpty() &&
                tutorState.phone.value.isNotEmpty() &&
                tutorState.address.value.isNotEmpty() &&
                tutorState.selectedOptionSex.value.isNotEmpty() )) {
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
                enabled = !tutorState.createdTutor.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                onClick = {
                    tutorState.createdTutor.value = true
                    onCreateTutor(tutorState.name.value, tutorState.surnames.value, tutorState.address.value,
                        tutorState.phone.value, tutorState.birthday.value, tutorState.selectedOptionEtnician.value,
                        tutorState.selectedOptionSex.value, tutorState.selectedOptionMaleRelations.value,
                        tutorState.selectedOptionWomanStatus.value, tutorState.weeks.value, tutorState.selectedOptionChildMinor.value,
                        tutorState.armCircunference.value, tutorState.babyAge.value, tutorState.status.value, tutorState.observations.value)

                },
            ) {
                Text(stringResource(R.string.save), color = colorResource(R.color.white), style = MaterialTheme.typography.h5)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}



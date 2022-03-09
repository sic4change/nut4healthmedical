package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors

import DatePickerView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun TutorItemDetailScreen(tutorState: TutorState, loading: Boolean = false, tutorItem: Tutor? ) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator()
        }
        if (tutorItem != null) {
            TutorItemDetailScaffold(
                tutorItem = tutorItem
            ) { padding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding)
                ) {
                    item {
                        Header(tutorState = tutorState)
                    }
                    /*item.references.forEach {
                        val (icon, @StringRes stringRes) = it.type.createUiData()
                        section(icon, stringRes, it.references)
                    }*/
                }
            }
        }

    }

}

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalCoilApi
@Composable
private fun Header(tutorState: TutorState) {

    val sexs = listOf(
        stringResource(R.string.female), stringResource(R.string.Male), stringResource(
            R.string.Undefined),)

    val etnicians = listOf(
        stringResource(R.string.white), stringResource(R.string.black), stringResource(
            R.string.mestizo),)

    val pregnants = listOf(stringResource(R.string.pregnant), stringResource(R.string.no_pregnant),)

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorState.name.value,
            onValueChange = {tutorState.name.value = it},
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Person, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})})
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorState.surnames.value,
            onValueChange = {tutorState.surnames.value = it},
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Person, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})})
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorState.address.value,
            onValueChange = {tutorState.address.value = it},
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Place, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})})
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorState.phone.value,
            onValueChange = {tutorState.phone.value = it},
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Default.Phone, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})})
        Spacer(modifier = Modifier.height(16.dp))
        DatePickerView(
            context = LocalContext.current,
            value = SimpleDateFormat("dd/MM/yyyy").format(tutorState.birthday.value),
            setValue = { tutorState.birthday.value = SimpleDateFormat("dd-MM-yyyy").parse(it)}
        )
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
                                    tutorState.sex.value = it
                                },
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
                    Icon(Icons.Default.EmojiPeople, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable {   })},
            )
            ExposedDropdownMenu(
                expanded = tutorState.expandedSex.value,
                onDismissRequest = {
                    tutorState.expandedSex.value = false
                }
            ) {
                sexs.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            tutorState.selectedOptionSex.value = selectionOption
                            tutorState.expandedSex.value = false
                        }
                    ) {
                        Text(text = selectionOption, color = colorResource(R.color.colorPrimary))
                    }
                }
            }
        }
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
            )
            ExposedDropdownMenu(
                expanded = tutorState.expandedEtnician.value,
                onDismissRequest = {
                    tutorState.expandedEtnician.value = false
                }
            ) {
                etnicians.forEach { selectionOption2 ->
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
            expanded = tutorState.expandedPregnant.value,
            onExpandedChange = {
                tutorState.expandedPregnant.value = !tutorState.expandedPregnant.value
            }
        ) {
            TextField(
                readOnly = true,
                value = tutorState.selectedOptionPregnant.value,
                onValueChange = {tutorState.selectedOptionPregnant.value = it
                    tutorState.selectedOptionPregnant.value = it },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = tutorState.expandedPregnant.value
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
            )
            ExposedDropdownMenu(
                expanded = tutorState.expandedPregnant.value,
                onDismissRequest = {
                    tutorState.expandedPregnant.value = false
                }
            ) {
                pregnants.forEach { selectionOption3 ->
                    DropdownMenuItem(
                        onClick = {
                            tutorState.selectedOptionPregnant.value = selectionOption3
                            tutorState.expandedPregnant.value = false
                        }
                    ) {
                        Text(text = selectionOption3, color = colorResource(R.color.colorPrimary))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorState.observations.value,
            onValueChange = {tutorState.observations.value = it},
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
            ),
            maxLines = 5,
            singleLine = false,
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Edit, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
        )
        Spacer(modifier = Modifier.height(16.dp))

    }
}



package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import java.text.SimpleDateFormat

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun TutorItemDetailScreen(tutorState: TutorState, loading: Boolean = false,
                          tutorItem: Tutor?, onEditClick: (Tutor) -> Unit,
onClickChild: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator()
        }
        if (tutorItem != null) {
            TutorItemDetailScaffold(
                tutorItem = tutorItem,
                onClickEdit = onEditClick,
                onChildClick = onClickChild
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
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.full_transparent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.full_transparent),
                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
            ),
            onValueChange = {}, readOnly = true,
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Person, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.name), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorState.surnames.value,
            onValueChange = {}, readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.full_transparent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.full_transparent),
                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Person, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.surnames), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorState.address.value,
            onValueChange = {}, readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.full_transparent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.full_transparent),
                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Place, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.address), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorState.phone.value,
            onValueChange = {}, readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.full_transparent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.full_transparent),
                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Default.Phone, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.phone), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        /*DatePickerView(
            context = LocalContext.current,
            value = SimpleDateFormat("dd/MM/yyyy").format(tutorState.birthday.value),
            setValue = { tutorState.birthday.value = SimpleDateFormat("dd-MM-yyyy").parse(it)}
        )*/
        TextField(value = SimpleDateFormat("dd/MM/yyyy").format(tutorState.birthday.value),
            onValueChange = {}, readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.full_transparent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.full_transparent),
                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Default.Cake, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.birthdate), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = SimpleDateFormat("dd/MM/yyyy").format(tutorState.createdDate.value),
            onValueChange = {}, readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.full_transparent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.full_transparent),
                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Default.CalendarToday, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.creation_date), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = SimpleDateFormat("dd/MM/yyyy HH:mm").format(tutorState.lastDate.value),
            onValueChange = {}, readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.full_transparent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.full_transparent),
                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Default.CalendarToday, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.last_date), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorState.etnician.value,
            onValueChange = {}, readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.full_transparent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.full_transparent),
                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Face, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.etnician), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = tutorState.sex.value,
            onValueChange = {}, readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.full_transparent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.full_transparent),
                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
            ),
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Default.EmojiPeople, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.sex), color = colorResource(R.color.disabled_color)) })
        Spacer(modifier = Modifier.height(16.dp))
        /*ExposedDropdownMenuBox(
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
        }*/
        AnimatedVisibility(visible = (tutorState.sex.value.equals(stringResource(R.string.female)))) {
            TextField(value = tutorState.pregnant.value,
                onValueChange = {}, readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.colorPrimary),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.full_transparent),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.full_transparent),
                    unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                ),
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(Icons.Filled.PregnantWoman, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                label = { Text(stringResource(R.string.pregnant), color = colorResource(R.color.disabled_color)) })
        }
        AnimatedVisibility(visible = (tutorState.sex.value.equals(stringResource(R.string.female)))) {
            Spacer(modifier = Modifier.height(16.dp))
        }
        AnimatedVisibility(visible = (tutorState.pregnant.value.equals(stringResource(R.string.pregnant)))) {
            TextField(value = tutorState.weeks.value.toString(),
                onValueChange = {}, readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.colorPrimary),
                    backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    cursorColor = colorResource(R.color.full_transparent),
                    disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                    focusedIndicatorColor = colorResource(R.color.full_transparent),
                    unfocusedIndicatorColor = colorResource(R.color.full_transparent),
                ),
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
                leadingIcon = {
                    Icon(Icons.Filled.CalendarViewWeek, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                label = { Text(stringResource(R.string.weeks), color = colorResource(R.color.disabled_color)) })

        }
        AnimatedVisibility(visible = (tutorState.pregnant.value.equals(stringResource(R.string.pregnant)))) {
            Spacer(modifier = Modifier.height(16.dp))
        }
        TextField(value = tutorState.observations.value,
            onValueChange = {}, readOnly = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(R.color.colorPrimary),
                backgroundColor = colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                cursorColor = colorResource(R.color.colorAccent),
                disabledLabelColor =  colorResource(androidx.browser.R.color.browser_actions_bg_grey),
                focusedIndicatorColor = colorResource(R.color.colorAccent),
                unfocusedIndicatorColor = colorResource(R.color.full_transparent),
            ),
            maxLines = 5,
            singleLine = false,
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            leadingIcon = {
                Icon(Icons.Filled.Edit, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
            label = { Text(stringResource(R.string.observations), color = colorResource(R.color.disabled_color)) }
        )
        Spacer(modifier = Modifier.height(16.dp))

    }
}



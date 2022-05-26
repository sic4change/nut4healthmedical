package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail

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
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.TutorState
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun TutorItemDetailScreen(tutorState: TutorState, loading: Boolean = false,
                          tutorItem: Tutor?, onEditClick: (Tutor) -> Unit,
                          onDeleteClick: () -> Unit,
                          onClickChild: (Tutor) -> Unit) {
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
        if (tutorItem != null) {
            TutorItemDetailScaffold(
                tutorState = tutorState,
                tutorItem = tutorItem,
                onClickEdit = onEditClick,
                onChildClick = onClickChild,
                onClickDelete = onDeleteClick
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
        val splitDate = SimpleDateFormat("dd/MM/yyyy").format(tutorState.birthday.value).split("/")
        val yearsLabel = ChronoUnit.YEARS.between(
            ZonedDateTime.parse(splitDate[2] + "-" +
                splitDate[1] + "-" + splitDate[0] + "T00:00:00.000Z"), ZonedDateTime.now())
        TextField(value = "${SimpleDateFormat("dd/MM/yyyy").format(tutorState.birthday.value)} ≈${yearsLabel} ${stringResource(R.string.years)}",
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
        AnimatedVisibility(visible = (tutorState.sex.value == "Femenino" || tutorState.sex.value == "Femme")) {
            TextField(value = tutorState.childMinor.value,
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
                    Icon(Icons.Filled.ChildCare, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                label = { Text(stringResource(R.string.child_minor_six_month), color = colorResource(R.color.disabled_color)) })
        }
        AnimatedVisibility(visible = (tutorState.sex.value == "Femenino" || tutorState.sex.value == "Femme")) {
            Spacer(modifier = Modifier.height(16.dp))
        }
        AnimatedVisibility(visible = (tutorState.sex.value == "Femenino" || tutorState.sex.value == "Femme")) {
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
        AnimatedVisibility(visible = (tutorState.sex.value == "Femenino" || tutorState.sex.value == "Femme")) {
            Spacer(modifier = Modifier.height(16.dp))
        }
        AnimatedVisibility(
            visible = ((tutorState.sex.value == "Femenino" || tutorState.sex.value == "Femme") &&
                    (tutorState.pregnant.value == "Embarazada" || tutorState.pregnant.value == "Enceinte"))) {
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
        AnimatedVisibility(visible = ((tutorState.sex.value == "Femenino" || tutorState.sex.value == "Femme") &&
                (tutorState.pregnant.value == "Embarazada" || tutorState.pregnant.value == "Enceinte"))) {
            Spacer(modifier = Modifier.height(16.dp))
        }
        AnimatedVisibility(visible = (tutorState.selectedOptionPregnant.value == stringResource(R.string.pregnant) ||
                tutorState.selectedOptionChildMinor.value == stringResource(R.string.child_minor))) {
            TextField(value = tutorState.height.value.toString(),
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
                    Icon(Icons.Filled.Height, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                label = { Text(stringResource(R.string.height), color = colorResource(R.color.disabled_color)) })
        }
        AnimatedVisibility(visible = (tutorState.selectedOptionPregnant.value == stringResource(R.string.pregnant) ||
                tutorState.selectedOptionChildMinor.value == stringResource(R.string.child_minor))) {
            Spacer(modifier = Modifier.height(16.dp))
        }
        AnimatedVisibility(visible = (tutorState.selectedOptionPregnant.value == stringResource(R.string.pregnant) ||
                tutorState.selectedOptionChildMinor.value == stringResource(R.string.child_minor))) {
            TextField(value = tutorState.weight.value.toString(),
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
                    Icon(Icons.Filled.SpaceBar, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                label = { Text(stringResource(R.string.weight), color = colorResource(R.color.disabled_color)) })
        }
        AnimatedVisibility(visible = (tutorState.selectedOptionPregnant.value == stringResource(R.string.pregnant) ||
                tutorState.selectedOptionChildMinor.value == stringResource(R.string.child_minor))) {
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageDeleteTutor(showDialog: Boolean, setShowDialog: () -> Unit, tutorId: String, onDeleteTutor: (String) -> Unit, onDeleteTutorSelected: () -> Unit) {
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
                        onDeleteTutor(tutorId)
                        onDeleteTutorSelected()
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
                Text(stringResource(R.string.delete_tutor_question))
            },
        )
    }

}



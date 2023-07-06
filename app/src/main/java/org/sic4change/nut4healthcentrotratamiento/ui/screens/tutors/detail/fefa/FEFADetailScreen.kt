package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.fefa

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.TutorState
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.TutorItemDetailScaffold
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.TutorSummaryItem
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun FEFAItemDetailScreen(fefaState: TutorState,
                         loading: Boolean = false,
                         tutorItem: Tutor?,
                         onEditClick: (Tutor) -> Unit) {
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
                tutorState = fefaState,
                tutorItem = tutorItem,
                onClickEdit = onEditClick
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding)
                ) {
                    FEFAView(
                        tutorItem = tutorItem,
                        tutorState = fefaState)
                }
            }

        }
    }

}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@ExperimentalCoilApi
@Composable
private fun FEFAView(
    tutorItem: Tutor,
    tutorState: TutorState)  {

    val SEXS = listOf(
        stringResource(R.string.female), stringResource(R.string.male)
    )

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(16.dp))

            TutorSummaryItem(
                item = tutorItem,
                expanded = tutorState.expandedDetail.value,
                onExpandDetail = { tutorState.expandContractDetail() }
            )


            if (tutorState.expandedDetail.value) {
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
                    label = { Text(stringResource(R.string.sex), color = colorResource(R.color.disabled_color)) })
                Spacer(modifier = Modifier.height(16.dp))
                AnimatedVisibility(visible = (tutorState.sex.value == stringResource(R.string.male))) {
                    TextField(value = tutorState.maleRelation.value,
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
                            Icon(painterResource(R.mipmap.ic_relation), null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                        label = { Text(stringResource(R.string.relation), color = colorResource(R.color.disabled_color)) })
                }
                AnimatedVisibility(visible = (tutorState.sex.value == stringResource(R.string.male))) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                AnimatedVisibility(visible = (tutorState.selectedOptionSex.value == stringResource(R.string.female))) {
                    TextField(value = tutorState.womanStatus.value,
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
                        label = { Text(stringResource(R.string.status), color = colorResource(R.color.disabled_color)) })
                }
                AnimatedVisibility(visible = (tutorState.selectedOptionSex.value == stringResource(R.string.female))) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                AnimatedVisibility(visible = (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant) ||
                        tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant_and_infant))) {
                    TextField(value = tutorState.weeks.value,
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
                            Icon(Icons.Filled.ViewWeek, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                        label = { Text(stringResource(R.string.weeks), color = colorResource(R.color.disabled_color)) })
                }
                AnimatedVisibility(visible = (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant) ||
                        tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant_and_infant))) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                AnimatedVisibility(visible = (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant))) {
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
                AnimatedVisibility(visible = (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant))) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                AnimatedVisibility(visible = (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.infant) ||
                        tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant_and_infant))) {
                    TextField(value = tutorState.babyAge.value,
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
                            Icon(Icons.Filled.ChildFriendly, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { /* .. */})},
                        label = { Text(stringResource(R.string.baby_age), color = colorResource(R.color.disabled_color)) })
                }
                AnimatedVisibility(visible = (tutorState.selectedOptionWomanStatus.value == stringResource(R.string.infant) ||
                        tutorState.selectedOptionWomanStatus.value == stringResource(R.string.pregnant_and_infant))) {
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
            Spacer(modifier = Modifier.height(16.dp))

        }

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





package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.ui.commons.StringResourcesUtil.Companion.doesStringMatchAnyLocale
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.ChildListItem
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.FEFAListItem
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.TutorState
import java.text.SimpleDateFormat

import org.sic4change.nut4healthcentrotratamiento.ui.commons.getYears

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun TutorItemDetailScreen(tutorState: TutorState,
                          loading: Boolean = false,
                          tutorItem: Tutor?,
                          childs: List<Child>?,
                          onFEFAClick: (Tutor) -> Unit,
                          onEditClick: (Tutor) -> Unit,
                          onCreateChildClick: (Tutor) -> Unit,
                          onItemClick: (Child) -> Unit,
                          onClickDetail: (Child) -> Unit,
                          onDeleteChild: (String) -> Unit,
                          onClickEdit: (Child) -> Unit,
                          onClickDelete: (Child) -> Unit,
                          onClickDeath: (Child) -> Unit,
                          onConfirmationChildDeath: (String) -> Unit,){
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
                onClickEdit = onEditClick
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding)
                ) {
                    TutorView(
                        tutorItem = tutorItem,
                        tutorState = tutorState,
                        childs = childs,
                        onFEFAClick = onFEFAClick,
                        onItemClick = onItemClick,
                        onCreateChildClick = onCreateChildClick,
                        onClickDetail = onClickDetail,
                        onClickDelete = onClickDelete,
                        onClickEdit = onClickEdit,
                        onDeleteChild  = onDeleteChild,
                        onClickDeath = onClickDeath)
                }
            }

        }
    }

}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@ExperimentalCoilApi
@Composable
private fun TutorView(
    tutorItem: Tutor,
    tutorState: TutorState,
    childs: List<Child>?,
    onFEFAClick: (Tutor) -> Unit,
    onItemClick: (Child) -> Unit,
    onCreateChildClick: (Tutor) -> Unit,
    onClickDetail: (Child) -> Unit,
    onDeleteChild: (String) -> Unit,
    onClickEdit: (Child) -> Unit,
    onClickDelete: (Child) -> Unit,
    onClickDeath: (Child) -> Unit) {

    val SEXS = listOf(
        stringResource(R.string.female), stringResource(R.string.male)
    )

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            if (doesStringMatchAnyLocale(LocalContext.current, "pregnant", tutorState.womanStatus.value) ||
                doesStringMatchAnyLocale(LocalContext.current, "infant", tutorState.womanStatus.value) ||
                doesStringMatchAnyLocale(LocalContext.current, "pregnant_and_infant", tutorState.womanStatus.value)) {
                Text(
                    text = stringResource(R.string.fefa),
                    color = colorResource(R.color.colorPrimary),
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 0.dp, end = 0.dp, start = 0.dp)
                )
            } else {
                Text(
                    text = stringResource(R.string.tutor),
                    color = colorResource(R.color.colorPrimary),
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 0.dp, end = 0.dp, start = 0.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (doesStringMatchAnyLocale(LocalContext.current, "pregnant", tutorState.womanStatus.value) ||
                doesStringMatchAnyLocale(LocalContext.current, "infant", tutorState.womanStatus.value) ||
                doesStringMatchAnyLocale(LocalContext.current, "pregnant_and_infant", tutorState.womanStatus.value)) {
                FEFAListItem(
                    item = tutorItem,
                    expanded = tutorState.expandedDetail.value,
                    onExpandDetail = { tutorState.expandContractDetail() },
                    modifier = Modifier.clickable { onFEFAClick(tutorItem) },
                )
            } else {
                TutorSummaryItem(
                    item = tutorItem,
                    expanded = tutorState.expandedDetail.value,
                    onExpandDetail = { tutorState.expandContractDetail() }
                )
            }


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
                val yearsLabel = getYears(tutorState.birthday.value!!)
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
                AnimatedVisibility(visible = doesStringMatchAnyLocale(LocalContext.current, "male", tutorState.sex.value)) {
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
                AnimatedVisibility(visible = doesStringMatchAnyLocale(LocalContext.current, "male", tutorState.sex.value)) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                AnimatedVisibility(visible = doesStringMatchAnyLocale(LocalContext.current, "female", tutorState.selectedOptionSex.value)) {
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
                AnimatedVisibility(visible = doesStringMatchAnyLocale(LocalContext.current, "female", tutorState.selectedOptionSex.value)) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                AnimatedVisibility(visible = (doesStringMatchAnyLocale(LocalContext.current, "pregnant", tutorState.selectedOptionWomanStatus.value)  ||
                        doesStringMatchAnyLocale(LocalContext.current, "pregnant_and_infant", tutorState.selectedOptionWomanStatus.value))) {
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
                AnimatedVisibility(visible = (doesStringMatchAnyLocale(LocalContext.current, "pregnant", tutorState.selectedOptionWomanStatus.value) ||
                        doesStringMatchAnyLocale(LocalContext.current, "pregnant_and_infant", tutorState.selectedOptionWomanStatus.value))) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                AnimatedVisibility(visible = doesStringMatchAnyLocale(LocalContext.current, "pregnant", tutorState.selectedOptionWomanStatus.value)) {
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
                AnimatedVisibility(visible = doesStringMatchAnyLocale(LocalContext.current, "pregnant", tutorState.selectedOptionWomanStatus.value)) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                AnimatedVisibility(visible = (doesStringMatchAnyLocale(LocalContext.current, "infant", tutorState.selectedOptionWomanStatus.value) ||
                        doesStringMatchAnyLocale(LocalContext.current, "pregnant_and_infant", tutorState.selectedOptionWomanStatus.value))) {
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
                AnimatedVisibility(visible = (doesStringMatchAnyLocale(LocalContext.current, "infant", tutorState.selectedOptionWomanStatus.value)  ||
                        doesStringMatchAnyLocale(LocalContext.current, "pregnant_and_infant", tutorState.selectedOptionWomanStatus.value))) {
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
            if (childs != null && childs.isEmpty()) {
                Row( modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp, 32.dp, 0.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.weight(1f, true),
                        text = stringResource(R.string.no_childs),
                        color = colorResource(R.color.colorPrimary),
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Start,
                        maxLines = 3
                    )
                    Image(
                        modifier = Modifier.size(64.dp),
                        painter = painterResource(id = R.mipmap.ic_childs),
                        contentDescription = null,
                    )
                }
            } else if (childs != null && childs.isNotEmpty()){
                Row( modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp, 32.dp, 0.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.weight(1f, true),
                        text = stringResource(R.string.childs),
                        color = colorResource(R.color.colorPrimary),
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Start,
                        maxLines = 3,
                    )
                    Image(
                        modifier = Modifier.size(64.dp),
                        painter = painterResource(id = R.mipmap.ic_childs),
                        contentDescription = null
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (childs == null) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(color = colorResource(R.color.colorPrimaryDark))
                }
            }
        }
        if (childs != null){
            items(childs) {
                ChildListItem(
                    item = it,
                    modifier = Modifier.clickable { onItemClick(it) },
                    onClickDetail = onClickDetail,
                    onClickEdit = onClickEdit,
                    onClickDelete = onClickDelete,
                    onClickDeath = onClickDeath
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                    onClick = {onCreateChildClick(tutorItem)},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        stringResource(R.string.create_child),
                        color = colorResource(R.color.white),
                        style = MaterialTheme.typography.h5,
                    )
                }
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageDeleteChild(showDialog: Boolean, setShowDialog: () -> Unit, childId: String, onDeleteChild: (String) -> Unit, tutorId: String, onDeleteChildSelected: (String) -> Unit) {
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
                        onDeleteChild(childId)
                        onDeleteChildSelected(tutorId)
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
                Text(stringResource(R.string.delete_child_question))
            },
        )
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageConfirmDeathChild(showDialog: Boolean, setShowDialog: () -> Unit, childId: String, onConfirmDeathChild: (String) -> Unit) {
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
                        onConfirmDeathChild(childId)
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
                Text(stringResource(R.string.death_notification_confirmation))
            },
        )
    }

}



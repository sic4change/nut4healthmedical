package org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.detail

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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.MainActivity
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.CaseListItem
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.ChildState
import java.text.SimpleDateFormat

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ChildItemDetailScreen(
    childState: ChildState,
    loading: Boolean = false,
    childItem: Child?,
    cases: List<Case>?,
    isOneCaseOpen: Boolean,
    onEditClick: (Child) -> Unit,
    onClickTransfered: (Case) -> Unit,
    onCreateCaseClick: (String, String, String) -> Unit,
    onItemClick: (Case) -> Unit,
    onClickDetail: (Case) -> Unit,
    onDeleteCase: (String) -> Unit,

    onClickEdit: (Case) -> Unit,
    onClickDelete: (Case) -> Unit) {

    if (loading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = colorResource(R.color.colorPrimaryDark))
        }
    }
    //refresh notification case
    MainActivity.notificationChildId = ""

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (childItem != null) {
            ChildItemDetailScaffold(
                childState = childState,
                childItem = childItem,
                onClickEdit = onEditClick
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding)
                ) {
                    ChildView(
                        childItem = childItem,
                        childState = childState,
                        cases = cases,
                        isOneCaseOpen = isOneCaseOpen,
                        onItemClick = onItemClick,
                        onCreateCaseClick = onCreateCaseClick,
                        onClickDetail = onClickDetail,
                        onClickDelete = onClickDelete,
                        onClickTransfered = onClickTransfered,
                        onClickEdit = onClickEdit,
                        onDeleteChild  = onDeleteCase
                    )
                }
            }

        }



    }

}

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalCoilApi
@Composable
private fun ChildView(
    childItem: Child,
    childState: ChildState,
    cases: List<Case>?,
    isOneCaseOpen: Boolean,
    onItemClick: (Case) -> Unit,
    onCreateCaseClick: (String, String, String) -> Unit,
    onClickDetail: (Case) -> Unit,
    onDeleteChild: (String) -> Unit,
    onClickEdit: (Case) -> Unit,
    onClickDelete: (Case) -> Unit,
    onClickTransfered: (Case) -> Unit){

    val SEXS = listOf(
        stringResource(R.string.female), stringResource(R.string.male)
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        if (!childState.disabledView.value){
            item {
                Spacer(modifier = Modifier.height(16.dp))
                ChildSummaryItem(
                    item = childItem,
                    expanded = childState.expandedDetail.value,
                    onExpandDetail = { childState.expandContractDetail() }
                )
                if (childState.expandedDetail.value) {
                    TextField(value = childState.brothers.value.toString(),
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
                            Icon(painterResource(R.mipmap.ic_brothers), null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
                        label = { Text(stringResource(R.string.child_brothers), color = colorResource(R.color.disabled_color)) })

                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(value = SimpleDateFormat("dd/MM/yyyy").format(childState.createdDate.value),
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
                            Icon(Icons.Default.CalendarToday, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
                        label = { Text(stringResource(R.string.creation_date), color = colorResource(R.color.disabled_color)) })
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(value = SimpleDateFormat("dd/MM/yyyy HH:mm").format(childState.lastDate.value),
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
                            Icon(Icons.Default.CalendarToday, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
                        label = { Text(stringResource(R.string.last_date), color = colorResource(R.color.disabled_color)) })
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(value = childState.etnician.value,
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
                            Icon(Icons.Filled.Face, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
                        label = { Text(stringResource(R.string.etnician), color = colorResource(R.color.disabled_color)) })
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(value = childState.sex.value,
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
                            if (childState.selectedOptionSex.value == SEXS[0]) {
                                Icon(
                                    Icons.Filled.Female,
                                    null,
                                    tint = colorResource(R.color.colorPrimary),
                                    modifier = Modifier.clickable { /* .. */ })
                            } else if (childState.selectedOptionSex.value == SEXS[1]) {
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
                    TextField(value = childState.observations.value,
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
                            Icon(Icons.Filled.Edit, null, tint = colorResource(R.color.colorPrimary),  modifier = Modifier.clickable { })},
                        label = { Text(stringResource(R.string.observations), color = colorResource(R.color.disabled_color)) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                }
                if (cases != null && cases.isEmpty()) {
                    Row( modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp, 32.dp, 0.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.no_cases),
                            color = colorResource(R.color.colorPrimary),
                            style = MaterialTheme.typography.h4,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.weight(1f, true),
                            maxLines = 3
                        )
                        Image(
                            modifier = Modifier.size(78.dp).weight(1f),
                            painter = painterResource(id = R.mipmap.ic_cases),
                            contentDescription = null,
                        )
                    }
                } else if (cases != null && cases.isNotEmpty()){
                    Row( modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp, 32.dp, 0.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.weight(1f, true),
                            text = stringResource(R.string.casos),
                            color = colorResource(R.color.colorPrimary),
                            style = MaterialTheme.typography.h4,
                            textAlign = TextAlign.Start,
                            maxLines = 3
                        )
                        Image(
                            modifier = Modifier.size(64.dp),
                            painter = painterResource(id = R.mipmap.ic_cases),
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (cases == null) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(color = colorResource(R.color.colorPrimaryDark))
                    }
                }
            }
        }

        if (cases != null && !childState.disabledView.value){
            items(cases) {
                CaseListItem(
                    item = it,
                    modifier = Modifier.clickable { onItemClick(it) },
                    onClickDetail = onClickDetail,
                    onClickEdit = onClickEdit,
                    onClickDelete = onClickDelete,
                    onClickTransfered = onClickTransfered
                )
            }
        }

        if (!childState.disabledView.value){
            item {
                if (!isOneCaseOpen) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        val name = "${stringResource(R.string.caso)}_${if(cases == null) 1 else (cases?.size!!.plus(1))}"
                        val status = stringResource(R.string.open)
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.colorPrimary)),
                            onClick = {
                                onCreateCaseClick(name, status, "")
                                childState.enableDisableView()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            Text(
                                stringResource(R.string.create_case),
                                color = colorResource(R.color.white),
                                style = MaterialTheme.typography.h5,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageDeleteChild(showDialog: Boolean, setShowDialog: () -> Unit, childId: String, tutorId: String, onDeleteChild: (String) -> Unit, onDeleteChildSelected: (String) -> Unit) {
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
fun MessageDeleteCaseFromChild(showDialog: Boolean, setShowDialog: () -> Unit, caseId: String, onDeleteCase: (String) -> Unit, childId: String, onDeleteCaseSelected: (String) -> Unit) {
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
                        onDeleteCase(caseId)
                        onDeleteCaseSelected(childId)
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
                Text(stringResource(R.string.delete_case_question))
            },
        )
    }

}



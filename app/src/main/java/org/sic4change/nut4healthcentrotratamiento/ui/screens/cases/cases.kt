package org.sic4change.nut4healthcentrotratamiento.ui.screens.cases

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.create.CaseCreateViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.create.CaseItemCreateScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.detail.CaseDetailViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.detail.CaseItemDetailScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.detail.MessageDeleteCase
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.edit.CaseEditViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.edit.CaseItemEditScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.create.ChildCreateViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.create.ChildItemCreateScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.detail.ChildDetailViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.detail.ChildItemDetailScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.detail.MessageDeleteChild
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.edit.ChildEditViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.edit.ChildItemEditScreen


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun CasesScreen(viewModel: CasesViewModel = viewModel(), onClick: (Case) -> Unit,
                onCreateCaseClick: (String) -> Unit) {
    val casesState = rememberCasesState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.cases) {
        if (viewModelState.cases != null) {
            casesState.casesSize.value = viewModelState.cases!!.size
        }
    }


    NUT4HealthScreen {

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        onCreateCaseClick(viewModelState.childId)
                    },
                    backgroundColor = colorResource(R.color.colorPrimary),
                    content = {
                        Icon(Icons.Filled.Add,"", tint = Color.White)
                    }
                )
            },
        ) {
            CaseItemsListScreen(
                loading = viewModelState.loading,
                items = viewModelState.cases,
                onClick = onClick
            )
        }

    }

}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun CaseDetailScreen(viewModel: CaseDetailViewModel = viewModel(),
                     onEditCaseClick: (Case) -> Unit, onVisitsClick: (Case) -> Unit,
                     onDeleteCaseClick: (String) -> Unit) {
    val caseDetailState = rememberCasesState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.case) {
        if (viewModelState.case != null) {
            caseDetailState.id.value = viewModelState.case!!.id
            caseDetailState.childId.value = viewModelState.case!!.childId
            caseDetailState.name.value = viewModelState.case!!.name
            caseDetailState.status.value = viewModelState.case!!.status
            caseDetailState.visits.value = viewModelState.case!!.visits
            caseDetailState.observations.value = viewModelState.case!!.observations
        }
    }

    CaseItemDetailScreen(
        loading = viewModelState.loading,
        caseItem = viewModelState.case,
        caseState = caseDetailState,
        onEditClick = onEditCaseClick,
        onVisitsClick = onVisitsClick,
        onDeleteClick = onDeleteCaseClick
    )
    MessageDeleteCase(caseDetailState.deleteCase.value, caseDetailState::showDeleteQuestion,
        caseDetailState.id.value, caseDetailState.childId.value, viewModel::deleteCase, onDeleteCaseClick)
}


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun CaseCreateScreen(viewModel: CaseCreateViewModel = viewModel(), onCreateCase: (String) -> Unit) {
    val caseCreateState = rememberCasesState()
    val viewModelState by viewModel.state.collectAsState()


    LaunchedEffect(viewModelState.case) {
        if (viewModelState.case != null) {
            caseCreateState.id.value = viewModelState.case!!.id
            caseCreateState.childId.value = viewModelState.case!!.childId
            caseCreateState.name.value = viewModelState.case!!.name
            caseCreateState.observations.value = viewModelState.case!!.observations
        }
    }

    LaunchedEffect(viewModelState.created) {
        if (viewModelState.created) {
            onCreateCase(caseCreateState.childId.value)
        }
    }

    CaseItemCreateScreen(
        loading = viewModelState.loading,
        caseState = caseCreateState,
        onCreateCase = viewModel::createCase
    )
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun CaseEditScreen(viewModel: CaseEditViewModel = viewModel(), onEditCase: (String) -> Unit) {
    val caseEditState = rememberCasesState()
    val viewModelState by viewModel.state.collectAsState()


    LaunchedEffect(viewModelState.case) {
        if (viewModelState.case != null) {
            caseEditState.id.value = viewModelState.case!!.id
            caseEditState.childId.value = viewModelState.case!!.tutorId
            caseEditState.name.value = viewModelState.case!!.name
            caseEditState.status.value = viewModelState.case!!.status
            caseEditState.visits.value = viewModelState.case!!.visits
            caseEditState.lastDate.value = viewModelState.case!!.lastdate
            caseEditState.createdDate.value = viewModelState.case!!.createdate
            caseEditState.observations.value = viewModelState.case!!.observations
            caseEditState.selectedOptionStatus.value = viewModelState.case!!.status
        }
    }

    LaunchedEffect(viewModelState.editChild) {
        if (viewModelState.editChild) {
            onEditCase(caseEditState.id.value)
        }
    }

    CaseItemEditScreen(
        loading = viewModelState.loading,
        caseState = caseEditState,
        onEditCase = viewModel::editCase
    )
}




/*

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ChildEditScreen(viewModel: ChildEditViewModel = viewModel(), onEditChild: (String) -> Unit) {
    val childEditState = rememberChildsState()
    val viewModelState by viewModel.state.collectAsState()


    LaunchedEffect(viewModelState.child) {
        if (viewModelState.child != null) {
            childEditState.id.value = viewModelState.child!!.id
            childEditState.tutorId.value = viewModelState.child!!.tutorId
            childEditState.name.value = viewModelState.child!!.name
            childEditState.surnames.value = viewModelState.child!!.surnames
            childEditState.birthday.value = viewModelState.child!!.birthdate
            childEditState.lastDate.value = viewModelState.child!!.lastDate
            childEditState.createdDate.value = viewModelState.child!!.createDate
            childEditState.sex.value = viewModelState.child!!.sex
            childEditState.etnician.value = viewModelState.child!!.ethnicity
            childEditState.observations.value = viewModelState.child!!.observations
            childEditState.selectedOptionSex.value = viewModelState.child!!.sex
            childEditState.selectedOptionEtnician.value = viewModelState.child!!.ethnicity
        }
    }

    LaunchedEffect(viewModelState.editChild) {
        if (viewModelState.editChild) {
            onEditChild(childEditState.id.value)
        }
    }

    ChildItemEditScreen(
        loading = viewModelState.loading,
        childState = childEditState,
        onEditChild = viewModel::editChild
    )
}*/

package org.sic4change.nut4healthcentrotratamiento.ui.screens.cases


import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.detail.CaseDetailViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.detail.CaseItemDetailScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.detail.MessageDeleteCase
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.edit.CaseEditViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.edit.CaseItemEditScreen


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun CaseDetailScreen(viewModel: CaseDetailViewModel = viewModel(),
                     onEditCaseClick: (Case) -> Unit,
                     onCreateVisitClick: (Case) -> Unit,
                     onItemClick: (Visit) -> Unit,
                     onDeleteCaseClick: (String) -> Unit) {
    val caseDetailState = rememberCasesState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.case) {
        if (viewModelState.case != null) {
            caseDetailState.id.value = viewModelState.case!!.id
            caseDetailState.childId.value = viewModelState.case!!.childId
            caseDetailState.name.value = viewModelState.case!!.name
            caseDetailState.admissionType.value = viewModelState.case!!.admissionType
            caseDetailState.addmissionTypeServer.value = viewModelState.case!!.admissionTypeServer
            caseDetailState.status.value = viewModelState.case!!.status
            caseDetailState.status.value = viewModelState.case!!.admissionType
            caseDetailState.visits.value = viewModelState.case!!.visits
            caseDetailState.observations.value = viewModelState.case!!.observations
        }
    }

    CaseItemDetailScreen(
        loading = viewModelState.loading,
        caseItem = viewModelState.case,
        fefaItem = viewModelState.fefa,
        child = viewModelState.child,
        visits = viewModelState.visits,
        caseState = caseDetailState,
        onEditClick = onEditCaseClick,
        onCreateVisitClick = onCreateVisitClick,
        onItemClick = onItemClick
    )
    if (caseDetailState.childId.value != null) {
        MessageDeleteCase(caseDetailState.deleteCase.value, caseDetailState::showDeleteQuestion,
            caseDetailState.id.value, caseDetailState.childId.value!!, viewModel::deleteCase, onDeleteCaseClick)
    }

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
            caseEditState.childId.value = viewModelState.case!!.childId
            caseEditState.name.value = viewModelState.case!!.name
            caseEditState.status.value = viewModelState.case!!.status
            caseEditState.visits.value = viewModelState.case!!.visits
            caseEditState.lastDate.value = viewModelState.case!!.lastdate
            caseEditState.createdDate.value = viewModelState.case!!.createdate
            caseEditState.observations.value = viewModelState.case!!.observations
            caseEditState.selectedOptionStatus.value = viewModelState.case!!.status
        }
    }

    LaunchedEffect(viewModelState.editCase) {
        if (viewModelState.editCase && caseEditState.childId.value != null) {
            onEditCase(caseEditState.childId.value!!)
            viewModel.resetUpdateCase()
        }
    }

    CaseItemEditScreen(
        loading = viewModelState.loading,
        caseState = caseEditState,
        onEditCase = viewModel::editCase
    )
}



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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.create.CaseCreateViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.create.CaseItemCreateScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.detail.CaseDetailViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.detail.CaseItemDetailScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.detail.MessageDeleteCase
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.edit.CaseEditViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.edit.CaseItemEditScreen


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun CasesScreen(viewModel: CasesViewModel = viewModel(), onClick: (Case) -> Unit,
                onCreateCaseClick: (String) -> Unit, onGoToDetailClick: (Case) -> Unit) {
    val casesState = rememberCasesState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.cases) {
        if (viewModelState.cases != null) {
            casesState.casesSize.value = viewModelState.cases!!.size
            casesState.cases.value = viewModelState.cases
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
                items = casesState.cases.value,
                onClick = onClick,
                onGoToDetailClick = onGoToDetailClick
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
    val casePref = stringResource(R.string.caso)

    LaunchedEffect(viewModelState.case) {
        if (viewModelState.case != null) {
            caseCreateState.id.value = viewModelState.case!!.id
            caseCreateState.childId.value = viewModelState.case!!.childId
            caseCreateState.name.value = viewModelState.case!!.name
            caseCreateState.observations.value = viewModelState.case!!.observations
        }
    }

    LaunchedEffect(viewModelState.casesNumber) {
        caseCreateState.name.value = casePref + " " + viewModelState.casesNumber
    }

    LaunchedEffect(viewModelState.created) {
        if (viewModelState.created) {
            onCreateCase(caseCreateState.childId.value)
            viewModel.resetCreateCase()
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

    LaunchedEffect(viewModelState.editCase) {
        if (viewModelState.editCase) {
            onEditCase(caseEditState.id.value)
            viewModel.resetUpdateCase()
        }
    }

    CaseItemEditScreen(
        loading = viewModelState.loading,
        caseState = caseEditState,
        onEditCase = viewModel::editCase
    )
}



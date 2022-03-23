package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits

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
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.ui.NUT4HealthScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.create.CaseCreateViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.create.CaseItemCreateScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.detail.CaseDetailViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.detail.CaseItemDetailScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.detail.MessageDeleteCase
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.rememberCasesState
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.create.VisitCreateViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.create.VisitItemCreateScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.detail.MessageDeleteVisit
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.detail.VisitDetailViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.detail.VisitItemDetailScreen


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun VisitsScreen(viewModel: VisitsViewModel = viewModel(), onClick: (Visit) -> Unit,
                onCreateVisitClick: (String) -> Unit) {
    val casesState = rememberVisitsState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.visits) {
        if (viewModelState.visits != null) {
            casesState.visitsSize.value = viewModelState.visits!!.size
        }
    }


    NUT4HealthScreen {

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        onCreateVisitClick(viewModelState.caseId)
                    },
                    backgroundColor = colorResource(R.color.colorPrimary),
                    content = {
                        Icon(Icons.Filled.Add,"", tint = Color.White)
                    }
                )
            },
        ) {
            VisitItemsListScreen(
                loading = viewModelState.loading,
                items = viewModelState.visits,
                onClick = onClick
            )
        }

    }

}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun VisitDetailScreen(viewModel: VisitDetailViewModel = viewModel(),
                      onEditVisitClick: (Visit) -> Unit,
                      onDeleteVisitClick: (String) -> Unit) {
    val visitDetailState = rememberVisitsState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.visit) {
        if (viewModelState.visit != null) {
            visitDetailState.id.value = viewModelState.visit!!.id
            visitDetailState.caseId.value = viewModelState.visit!!.caseId
            visitDetailState.createdDate.value = viewModelState.visit!!.createdate
            visitDetailState.height.value = viewModelState.visit!!.height.toString()
            visitDetailState.weight.value = viewModelState.visit!!.weight.toString()
            visitDetailState.imc.value = viewModelState.visit!!.imc
            visitDetailState.armCircunference.value = viewModelState.visit!!.armCircunference
            visitDetailState.status.value = viewModelState.visit!!.status
            visitDetailState.observations.value = viewModelState.visit!!.observations
            visitDetailState.symtoms.value = viewModelState.visit!!.symtoms
            visitDetailState.treatments.value = viewModelState.visit!!.treatments
        }
    }

    VisitItemDetailScreen(
        loading = viewModelState.loading,
        visitItem = viewModelState.visit,
        visitState = visitDetailState,
        onEditClick = onEditVisitClick,
        onDeleteClick = onDeleteVisitClick
    )
    MessageDeleteVisit(visitDetailState.deleteVisit.value, visitDetailState::showDeleteQuestion,
        visitDetailState.id.value, visitDetailState.caseId.value, viewModel::deleteVisit, onDeleteVisitClick)
}


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun VisitCreateScreen(viewModel: VisitCreateViewModel = viewModel(), onCreateVisit: (String) -> Unit,
onChangeWeightOrHeight: (String, String) -> Unit) {
    val visitCreateState = rememberVisitsState()
    val viewModelState by viewModel.state.collectAsState()


    LaunchedEffect(viewModelState.visit) {
        if (viewModelState.visit != null) {
            visitCreateState.id.value = viewModelState.visit!!.id
            visitCreateState.caseId.value = viewModelState.visit!!.caseId
            visitCreateState.childId.value = viewModelState.visit!!.childId
            visitCreateState.tutorId.value = viewModelState.visit!!.tutorId
            visitCreateState.height.value = viewModelState.visit!!.height.toString()
            visitCreateState.weight.value = viewModelState.visit!!.weight.toString()
        }
    }

    LaunchedEffect(viewModelState.created) {
        if (viewModelState.created) {
            onCreateVisit(visitCreateState.caseId.value)
        }
    }

    LaunchedEffect(viewModelState.height) {
        onChangeWeightOrHeight(visitCreateState.height.value, visitCreateState.weight.value)
    }

    LaunchedEffect(viewModelState.weight) {
        onChangeWeightOrHeight(visitCreateState.height.value, visitCreateState.weight.value)
    }

    LaunchedEffect(viewModelState.imc) {
            visitCreateState.imc.value = viewModelState.imc!!
        if (visitCreateState.imc.value.equals(0.0) || visitCreateState.imc.value.equals(100.0)) {
            visitCreateState.status.value = "Normopeso"
        } else if (visitCreateState.imc.value.equals(-1.0) || visitCreateState.imc.value.equals(85.0)) {
            visitCreateState.status.value = "Peso Objetivo"
        } else if (visitCreateState.imc.value.equals(-1.5) || visitCreateState.imc.value.equals(80.0)) {
            visitCreateState.status.value = "Aguda Moderada"
        } else if (visitCreateState.imc.value.equals(-3.0) || visitCreateState.imc.value.equals(70.0)) {
            visitCreateState.status.value = "Aguda Severa"
        } else {
            visitCreateState.status.value = ""
        }
    }

    VisitItemCreateScreen(
        loading = viewModelState.loading,
        visitState = visitCreateState,
        onCreateVisit = viewModel::createVisit,
        onChangeWeightOrHeight = viewModel::checkDesnutrition
    )
}

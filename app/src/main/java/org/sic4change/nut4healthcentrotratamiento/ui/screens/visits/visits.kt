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
import androidx.compose.runtime.MutableState
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
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.edit.VisitEditViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.edit.VisitItemEditScreen


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

    LaunchedEffect(viewModelState.childDateMillis) {
        if (viewModelState.childDateMillis != null) {
            visitDetailState.childDateMillis.value = viewModelState.childDateMillis!!
        }
    }

    LaunchedEffect(viewModelState.case) {
        if (viewModelState.case != null) {
            visitDetailState.visitsSize.value = viewModelState.case!!.visits.toInt()
        }
    }

    LaunchedEffect(viewModelState.visits) {
        if (viewModelState.visits != null) {
            visitDetailState.visits.value = viewModelState.visits.toMutableList()
        }
    }

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
            visitDetailState.selectedEdema.value = viewModelState.visit!!.edema
            visitDetailState.selectedInfection.value = viewModelState.visit!!.infection
            visitDetailState.selectedEyes.value = viewModelState.visit!!.eyesDeficiency
            visitDetailState.selectedDeshidratation.value = viewModelState.visit!!.deshidratation
            visitDetailState.selectedVomitos.value = viewModelState.visit!!.vomiting
            visitDetailState.selectedDiarrea.value = viewModelState.visit!!.diarrhea
            visitDetailState.selectedFiebre.value = viewModelState.visit!!.fever
            visitDetailState.selectedTos.value = viewModelState.visit!!.cough
            visitDetailState.selectedRespiration.value = viewModelState.visit!!.respiratonStatus
            visitDetailState.selectedApetit.value = viewModelState.visit!!.appetiteTest
            visitDetailState.selectedTemperature.value = viewModelState.visit!!.temperature
            visitDetailState.vitamineAVaccinated.value = viewModelState.visit!!.vitamineAVaccinated
            visitDetailState.capsulesFerro.value = viewModelState.visit!!.acidfolicAndFerroVaccinated
            visitDetailState.amoxicilina.value = viewModelState.visit!!.amoxicilina
            visitDetailState.othersTratments.value = viewModelState.visit!!.otherTratments
            visitDetailState.selectedCartilla.value = viewModelState.visit!!.vaccinationCard
            visitDetailState.selectedRubeola.value = viewModelState.visit!!.rubeolaVaccinated
            visitDetailState.observations.value = viewModelState.visit!!.observations
            visitDetailState.vitamineAVaccinated.value = viewModelState.visit!!.vitamineAVaccinated
            visitDetailState.complications.value = viewModelState.visit!!.complications
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


@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun VisitCreateScreen(viewModel: VisitCreateViewModel = viewModel(), onCreateVisit: (String) -> Unit,
onChangeWeightOrHeight: (String, String) -> Unit) {
    val visitCreateState = rememberVisitsState()
    val viewModelState by viewModel.state.collectAsState()


    LaunchedEffect(viewModelState.childDateMillis) {
        if (viewModelState.childDateMillis != null) {
            visitCreateState.childDateMillis.value = viewModelState.childDateMillis!!
        }
    }

    LaunchedEffect(viewModelState.case) {
        if (viewModelState.case != null) {
            visitCreateState.visitsSize.value = viewModelState.case!!.visits.toInt()
        }
    }

    LaunchedEffect(viewModelState.visits) {
        if (viewModelState.visits != null) {
            visitCreateState.visits.value = viewModelState.visits.toMutableList()
        }
    }

    LaunchedEffect(viewModelState.visit) {
        if (viewModelState.visit != null) {
            visitCreateState.id.value = viewModelState.visit!!.id
            visitCreateState.caseId.value = viewModelState.visit!!.caseId
            visitCreateState.childId.value = viewModelState.visit!!.childId
            visitCreateState.tutorId.value = viewModelState.visit!!.tutorId
            visitCreateState.height.value = viewModelState.visit!!.height.toString()
            visitCreateState.weight.value = viewModelState.visit!!.weight.toString()
            visitCreateState.armCircunference.value = viewModelState.visit!!.armCircunference
            visitCreateState.vitamineAVaccinated.value = viewModelState.visit!!.vitamineAVaccinated
            visitCreateState.childDateMillis.value = viewModelState.childDateMillis!!
            visitCreateState.complications.value = viewModelState.visit!!.complications
            visitCreateState.childDateMillis.value = viewModelState.childDateMillis!!
        }
    }

    LaunchedEffect(viewModelState.complications) {
        visitCreateState.complications.value = viewModelState.complications.toMutableList()
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

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun VisitEditScreen(viewModel: VisitEditViewModel = viewModel(), onEditVisit: (String) -> Unit,
                    onChangeWeightOrHeight: (String, String) -> Unit) {
    val visitEditState = rememberVisitsState()
    val viewModelState by viewModel.state.collectAsState()

    LaunchedEffect(viewModelState.childDateMillis) {
        if (viewModelState.childDateMillis != null) {
            visitEditState.childDateMillis.value = viewModelState.childDateMillis!!
        }
    }

    LaunchedEffect(viewModelState.visit) {
        if (viewModelState.visit != null) {
            visitEditState.id.value = viewModelState.visit!!.id
            visitEditState.caseId.value = viewModelState.visit!!.caseId
            visitEditState.childId.value = viewModelState.visit!!.childId
            visitEditState.tutorId.value = viewModelState.visit!!.tutorId
            visitEditState.height.value = viewModelState.visit!!.height.toString()
            visitEditState.weight.value = viewModelState.visit!!.weight.toString()
            visitEditState.status.value = viewModelState.visit!!.status
            visitEditState.selectedEdema.value = viewModelState.visit!!.edema
            visitEditState.armCircunference.value = viewModelState.visit!!.armCircunference
            visitEditState.vitamineAVaccinated.value = viewModelState.visit!!.vitamineAVaccinated
            visitEditState.observations.value = viewModelState.visit!!.observations
            visitEditState.complications.value = viewModelState.visit!!.complications
        }
    }

    LaunchedEffect(viewModelState.complications) {
        visitEditState.complications.value = viewModelState.complications.toMutableList()
    }

    LaunchedEffect(viewModelState.editVisit) {
        if (viewModelState.editVisit) {
            onEditVisit(visitEditState.id.value)
        }
    }

    LaunchedEffect(viewModelState.height) {
        onChangeWeightOrHeight(visitEditState.height.value, visitEditState.weight.value)
    }

    LaunchedEffect(viewModelState.weight) {
        onChangeWeightOrHeight(visitEditState.height.value, visitEditState.weight.value)
    }

    LaunchedEffect(viewModelState.imc) {
        visitEditState.imc.value = viewModelState.imc!!
        if (visitEditState.imc.value.equals(0.0) || visitEditState.imc.value.equals(100.0)) {
            visitEditState.status.value = "Normopeso"
        } else if (visitEditState.imc.value.equals(-1.0) || visitEditState.imc.value.equals(85.0)) {
            visitEditState.status.value = "Peso Objetivo"
        } else if (visitEditState.imc.value.equals(-1.5) || visitEditState.imc.value.equals(80.0)) {
            visitEditState.status.value = "Aguda Moderada"
        } else if (visitEditState.imc.value.equals(-3.0) || visitEditState.imc.value.equals(70.0)) {
            visitEditState.status.value = "Aguda Severa"
        } else {
            visitEditState.status.value = ""
        }
    }

    VisitItemEditScreen(
        loading = viewModelState.loading,
        visitState = visitEditState,
        onEditVisit = viewModel::editVisit,
        onChangeWeightOrHeight = viewModel::checkDesnutrition
    )
}

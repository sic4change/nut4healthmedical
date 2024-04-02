package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.create.VisitCreateViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.create.VisitItemCreateScreen
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.detail.MessageDeleteVisit
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.detail.VisitDetailViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.detail.VisitItemDetailScreen

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

    LaunchedEffect(viewModelState.point) {
        if (viewModelState.point != null) {
            visitDetailState.point.value = viewModelState.point!!
        }
    }

    LaunchedEffect(viewModelState.case) {
        if (viewModelState.case != null) {
            visitDetailState.visitsSize.value = viewModelState.case!!.visits.toInt()
            visitDetailState.admissionType.value = viewModelState.case!!.admissionType
        }
    }

    LaunchedEffect(viewModelState.visits) {
        if (viewModelState.visits != null) {
            visitDetailState.visits.value = viewModelState.visits.toMutableList()
            if (viewModelState.visit != null) {
                visitDetailState.visitNumber.value = viewModel.getVisitNumber()
            }
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
            visitDetailState.selectedVitamineAVaccinated.value = viewModelState.visit!!.vitamineAVaccinated
            visitDetailState.selectedCapsulesFerro.value = viewModelState.visit!!.acidfolicAndFerroVaccinated
            visitDetailState.selectedAmoxicilina.value = viewModelState.visit!!.amoxicilina
            visitDetailState.othersTratments.value = viewModelState.visit!!.otherTratments
            visitDetailState.selectedCartilla.value = viewModelState.visit!!.vaccinationCard
            visitDetailState.selectedRubeola.value = viewModelState.visit!!.rubeolaVaccinated
            visitDetailState.selectedCapsulesFerro.value = viewModelState.visit!!.acidfolicAndFerroVaccinated
            visitDetailState.observations.value = viewModelState.visit!!.observations
            visitDetailState.complications.value = viewModelState.visit!!.complications
            if (viewModelState.visits != null && viewModelState.visits.isNotEmpty()) {
                visitDetailState.visitNumber.value = viewModel.getVisitNumber()
            }
        }
    }

    LaunchedEffect(viewModelState.fefa) {
        if (viewModelState.fefa != null) {
            visitDetailState.womanStatus.value = viewModelState.fefa!!.womanStatus
            visitDetailState.pregnantWeeks.value = viewModelState.fefa!!.weeks.toInt()
            visitDetailState.womanChildWeeks.value = viewModelState.fefa!!.babyAge.toInt()
        }
    }

    VisitItemDetailScreen(
        loading = viewModelState.loading,
        visitItem = viewModelState.visit,
        child = viewModelState.child,
        fefa = viewModelState.fefa,
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
                      onChangeWeightOrHeight: (String, String) -> Unit,
                      onCancelCreateVisit: () -> Unit, onCreateVisitSucessfull: (String, Boolean) -> Unit) {
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

    LaunchedEffect(viewModelState.point) {
        if (viewModelState.point != null) {
            visitCreateState.point.value = viewModelState.point!!
        }
    }

    LaunchedEffect(viewModelState.visits) {
        if (viewModelState.visits != null) {
            visitCreateState.visits.value = viewModelState.visits.toMutableList()
            visitCreateState.visitsSize.value = visitCreateState.visits.value.size
        }
    }

    LaunchedEffect(viewModelState.visit) {
        if (viewModelState.visit != null) {
            visitCreateState.id.value = viewModelState.visit!!.id
            visitCreateState.caseId.value = viewModelState.visit!!.caseId
            visitCreateState.childId.value = viewModelState.visit!!.childId!!
            visitCreateState.tutorId.value = viewModelState.visit!!.tutorId
            visitCreateState.height.value = viewModelState.visit!!.height.toString()
            visitCreateState.weight.value = viewModelState.visit!!.weight.toString()
            visitCreateState.armCircunference.value = viewModelState.visit!!.armCircunference
            visitCreateState.selectedVitamineAVaccinated.value = viewModelState.visit!!.vitamineAVaccinated
            if (viewModelState.childDateMillis != null) {
                visitCreateState.childDateMillis.value = viewModelState.childDateMillis!!
            }
            visitCreateState.complications.value = viewModelState.visit!!.complications
        }
    }

    LaunchedEffect(viewModelState.complications) {
        visitCreateState.complications.value = viewModelState.complications.toMutableList()
    }

    LaunchedEffect(viewModelState.caseClosed) {
        if (viewModelState.caseClosed != null) {
            if (viewModelState.caseClosed!!) {
                onCreateVisit(visitCreateState.caseId.value)
            }
            else {
                visitCreateState.showNextVisit()
            }
        }
    }

    LaunchedEffect(viewModelState.created) {
        if (viewModelState.created != null && viewModelState.derivation != null && viewModelState.caseClosed != null) {
            if (viewModelState.caseClosed!! && viewModelState.created!! && !viewModelState.derivation!!) {
                onCreateVisitSucessfull(visitCreateState.caseId.value, false)
            }
        }
    }

    LaunchedEffect(viewModelState.derivation) {
        if (viewModelState.derivation != null) {
            if (viewModelState.derivation!!) {
                onCreateVisitSucessfull(visitCreateState.caseId.value, true)
            }
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

    LaunchedEffect(viewModelState.fefa) {
        if (viewModelState.fefa != null) {
            visitCreateState.womanStatus.value = viewModelState.fefa!!.womanStatus
            visitCreateState.womanChildWeeks.value = viewModelState.fefa!!.babyAge.toInt()
            visitCreateState.pregnantWeeks.value = viewModelState.fefa!!.weeks.toInt()
        }
    }

    VisitItemCreateScreen(
        loading = viewModelState.loading,
        visitState = visitCreateState,
        child = viewModelState.child,
        fefa = viewModelState.fefa,
        onCreateVisit = viewModel::createVisit,
        onChangeWeightOrHeight = viewModel::checkDesnutrition,
        onRemoveTodayVisit = viewModel::removeTodayVisit,
        onCancelCreateVisit = onCancelCreateVisit,
        onCreateVisitSucessfull = onCreateVisitSucessfull
    )
}


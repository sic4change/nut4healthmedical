package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Complication
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Point
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Treatment
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.ui.screens.login.ErrorType
import java.util.*


@Composable
fun rememberVisitsState(
    id: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedDetail: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    caseId: MutableState<String> = rememberSaveable { mutableStateOf("") },
    childId: MutableState<String> = rememberSaveable { mutableStateOf("") },
    addmisionType: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedAddmisionType: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    tutorId: MutableState<String> = rememberSaveable { mutableStateOf("") },
    height: MutableState<String> = rememberSaveable { mutableStateOf("0") },
    weight: MutableState<String> = rememberSaveable { mutableStateOf("0") },
    imc: MutableState<Double> = rememberSaveable { mutableStateOf(0.0) },
    armCircunference: MutableState<Double> = rememberSaveable { mutableStateOf(30.0) },
    status: MutableState<String> = rememberSaveable { mutableStateOf("") },
    selectedEdema: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedEdema: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedInfection: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedInfection: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedEyes: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedEyes: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedDeshidratation: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedDeshidratation: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedVomitos: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedVomitos: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedDiarrea: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedDiarrea: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedFiebre: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedFiebre: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedTos: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedTos: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedRespiration: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedRespiration: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedApetit: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedApetit: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedTemperature: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedTemperature: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedVitamineAVaccinated: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedVitamineAVaccinated: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedCapsulesFerro: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedCapsulesFerro: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    expandedAmoxicilina: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedAmoxicilina: MutableState<String> = rememberSaveable { mutableStateOf("") },
    othersTratments: MutableState<String> = rememberSaveable { mutableStateOf("") },
    selectedCartilla: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedCartilla: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedRubeola: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedRubeola: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    observations: MutableState<String> = rememberSaveable { mutableStateOf("") },
    childDateMillis: MutableState<Long> = rememberSaveable { mutableStateOf(0) },
    treatments: MutableState<MutableList<Treatment>> = rememberSaveable {mutableStateOf(mutableListOf<Treatment>())},
    complications: MutableState<MutableList<Complication>> = rememberSaveable {mutableStateOf(mutableListOf())},
    createdDate: MutableState<Date> = rememberSaveable { mutableStateOf(Date()) },
    createdVisit:  MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    visits: MutableState<MutableList<Visit>> = rememberSaveable {mutableStateOf(mutableListOf<Visit>())},
    visitsSize: MutableState<Int> = rememberSaveable { mutableStateOf(0) },
    deleteVisit: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    currentStep: MutableState<Int> = rememberSaveable{mutableStateOf(1) },
    visitNumber: MutableState<Int> = rememberSaveable{mutableStateOf(1) },
    point: MutableState<Point> = remember{mutableStateOf<Point>(Point("", "", "", "", "", 0)) },
) = remember{ VisitState(id, expandedDetail, caseId, childId, tutorId, addmisionType, expandedAddmisionType,
    height, weight, imc, armCircunference, status, selectedEdema, expandedEdema, selectedInfection,
    expandedInfection, selectedEyes, expandedEyes, selectedDeshidratation, expandedDeshidratation,  selectedVomitos,
    expandedVomitos, selectedDiarrea, expandedDiarrea, selectedFiebre, expandedFiebre, selectedTos, expandedTos,
    selectedRespiration, expandedRespiration, selectedApetit, expandedApetit, selectedTemperature, expandedTemperature,
    selectedVitamineAVaccinated, expandedVitamineAVaccinated, selectedCapsulesFerro, expandedCapsulesFerro,
    selectedAmoxicilina, expandedAmoxicilina, othersTratments, selectedCartilla, expandedCartilla, selectedRubeola,
    expandedRubeola, observations, childDateMillis, treatments, complications, createdDate, createdVisit,
    visitsSize, visits, deleteVisit, currentStep, visitNumber, point,
) }



class VisitState(
    val id: MutableState<String>,
    val expandedDetail: MutableState<Boolean>,
    val caseId: MutableState<String>,
    val childId: MutableState<String>,
    val tutorId: MutableState<String>,
    val admissionType: MutableState<String>,
    val expandedAddmisionType: MutableState<Boolean>,
    val height: MutableState<String>,
    val weight: MutableState<String>,
    val imc: MutableState<Double>,
    val armCircunference: MutableState<Double>,
    val status: MutableState<String>,
    val selectedEdema: MutableState<String>,
    val expandedEdema: MutableState<Boolean>,
    val selectedInfection: MutableState<String>,
    val expandedInfecction: MutableState<Boolean>,
    val selectedEyes: MutableState<String>,
    val expandedEyes: MutableState<Boolean>,
    val selectedDeshidratation: MutableState<String>,
    val expandedDeshidratation: MutableState<Boolean>,
    val selectedVomitos: MutableState<String>,
    val expandedVomitos: MutableState<Boolean>,
    val selectedDiarrea: MutableState<String>,
    val expandedDiarrea: MutableState<Boolean>,
    val selectedFiebre: MutableState<String>,
    val expandedFiebre: MutableState<Boolean>,
    val selectedTos: MutableState<String>,
    val expandedTos: MutableState<Boolean>,
    val selectedRespiration: MutableState<String>,
    val expandedRespiration: MutableState<Boolean>,
    val selectedApetit: MutableState<String>,
    val expandedApetit: MutableState<Boolean>,
    val selectedTemperature: MutableState<String>,
    val expandedTemperature: MutableState<Boolean>,
    val selectedVitamineAVaccinated: MutableState<String>,
    val expandedVitamineAVaccinated: MutableState<Boolean>,
    val selectedCapsulesFerro: MutableState<String>,
    val expandedCapsulesFerro: MutableState<Boolean>,
    val selectedAmoxicilina: MutableState<String>,
    val expandedAmoxicilina: MutableState<Boolean>,
    val othersTratments: MutableState<String>,
    val selectedCartilla: MutableState<String>,
    val expandedCartilla: MutableState<Boolean>,
    val selectedRubeola: MutableState<String>,
    val expandedRubeola: MutableState<Boolean>,
    val observations: MutableState<String>,
    val childDateMillis: MutableState<Long>,
    val treatments: MutableState<MutableList<Treatment>>,
    val complications: MutableState<MutableList<Complication>>,
    val createdDate: MutableState<Date>,
    val createdVisit: MutableState<Boolean>,
    val visitsSize: MutableState<Int>,
    val visits: MutableState<MutableList<Visit>>,
    val deleteVisit: MutableState<Boolean>,
    val currentStep: MutableState<Int>,
    val visitNumber: MutableState<Int>,
    val point: MutableState<Point>,

) {

    fun incrementStep() {
        if (point.value.type != "Otro") {
            currentStep.value += 1
        } else {
            if (currentStep.value == 2) {
                currentStep.value = 4
            } else {
                currentStep.value += 1
            }
        }
    }

    fun decrementStep() {
        currentStep.value -= 1
    }

    fun expandContractDetail() {
        expandedDetail.value = !expandedDetail.value
    }

    fun showDeleteQuestion() {
        deleteVisit.value = !deleteVisit.value
    }

    fun formatHeightValue(value: String) {
        val temp = value.replace(",", ".").
        replace(" ", "").replace("-", "")
        if (temp != "." && temp.filter { it == '.' }.count() < 2) {
            height.value = temp
        }
    }

    fun formatWeightValue(value: String) {
        val temp = value.replace(",", ".").
            replace(" ", "").replace("-", "")
        if (temp != "." && temp.filter { it == '.' }.count() < 2) {
            weight.value = temp
        }
    }

    fun isOneComplicationSelected(): Boolean {
        return complications.value.filter { it.selected }.count() > 0
    }

    fun updateValuesByMUAC() {

    }


}

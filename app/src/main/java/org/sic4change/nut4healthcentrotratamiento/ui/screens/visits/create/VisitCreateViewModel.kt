package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.create

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.NavArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.*
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource
import java.util.*

class VisitCreateViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val caseId = savedStateHandle.get<String>(NavArg.ItemId.key) ?: "0"

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = _state.value.copy(
                loading = true,
                case = FirebaseDataSource.getCase(caseId),
                complications = FirebaseDataSource.getComplications(),
                visits = FirebaseDataSource.getVisits(caseId),
            )
            if (_state.value.case?.point != null) {
                _state.value = _state.value.copy(point = FirebaseDataSource.getPoint(_state.value.case!!.point))
            }
            if (_state.value.case?.childId?.isNotEmpty()!!) {
                _state.value = _state.value.copy(child = FirebaseDataSource.getChild(_state.value.case!!.childId!!))
                _state.value = _state.value.copy(
                    loading = false,
                    childDateMillis = _state.value.case?.let { FirebaseDataSource.getChild(it.childId!!)?.birthdate?.time }
                )
            } else {
                _state.value = _state.value.copy(fefa = FirebaseDataSource.getTutor(_state.value.case!!.fefaId!!))
                _state.value = _state.value.copy(
                    loading = false,
                    childDateMillis = _state.value.case?.let { FirebaseDataSource.getChild(it.fefaId!!)?.birthdate?.time }
                )
            }


        }
    }

    data class  UiState(
        val loading: Boolean = false,
        val point: Point? = null,
        val case: Case? = null,
        val child: Child? = null,
        val fefa: Tutor? = null,
        val visits: List<Visit> = emptyList(),
        val childDateMillis: Long? = 0,
        val visit: Visit? = null,
        val complications: List<Complication> = emptyList(),
        val height: Double? = null,
        val weight: Double? = null,
        var imc: Double? = 0.0,
        val created: Boolean = false,
    )

    fun removeTodayVisit(visitId: String) {
        viewModelScope.launch {
            FirebaseDataSource.deleteVisit(visitId)
        }
    }

    fun createVisit(admissionType: String, height: Double, weight: Double, arm_circunference: Double,
                    status: String, edema: String, respiratonStatus: String, appetiteTest: String,
                    infection: String, eyesDeficiency: String, deshidratation: String, vomiting: String,
                    diarrhea: String, fever: String, cough: String, temperature: String,
                    vitamineAVaccinated: String, acidfolicAndFerroVaccinated: String,
                    vaccinationCard: String, rubeolaVaccinated: String, amoxicilina: String, otherTratments: String,
                    complications: List<Complication>, observations: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            var imc = 0.0
            if (weight != null && height != null && weight > 0 && height > 0) {
                imc = FirebaseDataSource.checkDesnutrition(height, weight)
            }
            val visit = Visit("", caseId, caseId, null, caseId, Date(), admissionType, height,
                weight, imc, arm_circunference, status, edema, respiratonStatus, appetiteTest,infection,
                eyesDeficiency, deshidratation, vomiting, diarrhea, fever, cough, temperature,
                vitamineAVaccinated, acidfolicAndFerroVaccinated, vaccinationCard, rubeolaVaccinated,
                amoxicilina, otherTratments, complications.toMutableList(), observations, "")
            _state.value = _state.value.copy(loading = true, visit = visit)
            FirebaseDataSource.createVisit(visit)
            _state.value = _state.value.copy(loading = true, created = true)
        }
    }

    fun checkDesnutrition(height: String, weight: String, muac: Double, edema: String, complications: List<Complication>) {
        viewModelScope.launch {
            if (height.isNotEmpty() && weight.isNotEmpty() && height.toDouble() > 0 && weight.toDouble() > 0) {
                try {
                    _state.value = _state.value.copy(
                        complications = _state.value.complications,
                        imc = FirebaseDataSource.checkDesnutrition(height.toDouble(), weight.toDouble()),
                        childDateMillis = state.value.childDateMillis)
                    if (complications.filter { it.selected }.count() > 0 || (edema.isNotEmpty() && edema != "(0) No" && edema != "(0) Non" && edema != "(0) لا")) {
                        _state.value = _state.value.copy(imc = -3.0)
                    }
                    if (muac.toFloat() < 11.5 && _state.value.imc != -3.0) {
                        _state.value = _state.value.copy(imc = -3.0)
                    } else if (muac.toFloat() in 11.5..12.5 && (_state.value.imc!!.toFloat() > -3.0)) {
                        _state.value = _state.value.copy(imc = -1.5)
                    } else {
                        /*if ((_state.value.imc!!.toFloat() > -1.5)) {
                            _state.value = _state.value.copy(imc = 0.0)
                        }*/
                        //No hace falta hacer nada

                    }
                } catch (error: Error) {
                    println("error: ${error}")
                }
            } else {
                if (complications.filter { it.selected }.count() > 0 || (edema.isNotEmpty() && (edema != "(0) No" && edema != "(0) Non"&& edema != "(0) لا" ))) {
                    _state.value = _state.value.copy(
                        complications = _state.value.complications,
                        imc = -3.0,
                        childDateMillis = state.value.childDateMillis
                    )
                } else {
                    if (muac.toFloat() < 11.5) {
                        _state.value = _state.value.copy(imc = -3.0)
                    } else if (muac.toFloat() in 11.5..12.5) {
                        _state.value = _state.value.copy(imc = -1.5)
                    } else {
                        _state.value = _state.value.copy(imc = 0.0)
                    }
                }
            }
        }
    }


}
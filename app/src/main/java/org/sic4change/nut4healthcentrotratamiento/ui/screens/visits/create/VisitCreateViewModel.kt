package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.create

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaronat1.hackaton.ui.navigation.NavArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.*
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.VisitsViewModel
import java.util.*

class VisitCreateViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val caseId = savedStateHandle.get<String>(NavArg.ItemId.key) ?: "0"

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(
                loading = true,
                case = FirebaseDataSource.getCase(caseId),
                complications = FirebaseDataSource.getComplications(),
                visits = FirebaseDataSource.getVisits(caseId)
            )
            _state.value = _state.value.copy(
                loading = false,
                childDateMillis = _state.value.case?.let { FirebaseDataSource.getChild(it.childId)?.birthdate?.time }
            )
        }
    }

    data class  UiState(
        val loading: Boolean = false,
        val case: Case? = null,
        val visits: List<Visit> = emptyList(),
        val childDateMillis: Long? = 0,
        val visit: Visit? = null,
        val complications: List<Complication> = emptyList(),
        val height: Double? = null,
        val weight: Double? = null,
        var imc: Double? = 0.0,
        val created: Boolean = false,
    )

    fun createVisit(height: Double, weight: Double, arm_circunference: Double, status: String, edema: String,
                    respiratonStatus: String, appetiteTest: String, infection: String, eyesDeficiency: String,
                    deshidratation: String, vomiting: String, diarrhea: String, fever: String, cough: String,
                    temperature: String, vitamineAVaccinated: Boolean, acidfolicAndFerroVaccinated: Boolean,
                    vaccinationCard: String, rubeolaVaccinated: String, amoxicilina: Boolean, otherTratments: String,
                    complications: List<Complication>, observations: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val visit = Visit("", caseId, caseId, caseId, Date(), height, weight, 0.0,
                arm_circunference, status, edema, respiratonStatus, appetiteTest,infection,
                eyesDeficiency, deshidratation, vomiting, diarrhea, fever, cough, temperature,
                vitamineAVaccinated, acidfolicAndFerroVaccinated, vaccinationCard, rubeolaVaccinated,
                amoxicilina, otherTratments, complications.toMutableList(), observations, "")
            _state.value = _state.value.copy(loading = true, visit = visit)
            FirebaseDataSource.createVisit(visit)
            _state.value = _state.value.copy(loading = true, created = true)
        }
    }

    fun checkDesnutrition(height: String, weight: String, edema: String, complications: List<Complication>) {
        viewModelScope.launch {
            if (height.isNotEmpty() && weight.isNotEmpty()) {
                try {
                    _state.value = _state.value.copy(
                        complications = _state.value.complications,
                        imc = FirebaseDataSource.checkDesnutrition(height.toDouble(), weight.toDouble()),
                        childDateMillis = state.value.childDateMillis)
                    if (complications.filter { it.selected }.count() > 0 || (edema.isNotEmpty() && edema != "No" && edema != "Non")) {
                        _state.value = _state.value.copy(imc = -3.0)
                    }
                } catch (error: Error) {
                    println("error: ${error}")
                }
            }
        }
    }


}
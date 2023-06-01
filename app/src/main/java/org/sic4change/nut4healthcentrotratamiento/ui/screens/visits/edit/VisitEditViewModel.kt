package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaronat1.hackaton.ui.navigation.NavArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Complication
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Symtom
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Treatment
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.create.VisitCreateViewModel
import java.util.*

class VisitEditViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val id = savedStateHandle.get<String>(NavArg.ItemId.key) ?: "0"
    private var childId = ""

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(
                loading = true,
                visit = FirebaseDataSource.getVisit(id),
                treatments = FirebaseDataSource.getTreatments(),
                complications = FirebaseDataSource.getComplications(),
            )
            childId = _state.value.visit?.childId ?: "0"
            _state.value.treatments.forEach {
                if (_state.value.visit != null) {
                    _state.value.visit!!.treatments.forEach { treatment ->
                        if (it.id == treatment.id) {
                            it.selected = true
                        }
                    }
                }
            }
            _state.value.complications.forEach {
                if (_state.value.visit != null) {
                    _state.value.visit!!.complications.forEach { complication ->
                        if (it.id == complication.id) {
                            it.selected = true
                        }
                    }
                }
            }
            _state.value = UiState(
                loading = true,
                visit = _state.value.visit,
                treatments = _state.value.treatments,
                complications = _state.value.complications,
                childDateMillis = FirebaseDataSource.getChild(childId)?.birthdate?.time
            )
        }
    }


    data class  UiState(
        val loading: Boolean = false,
        val childDateMillis: Long? = 0,
        val visit: Visit? = null,
        val treatments: List<Treatment> = emptyList(),
        val complications: List<Complication> = emptyList(),
        val height: Double? = null,
        val weight: Double? = null,
        var imc: Double? = 0.0,
        val editVisit: Boolean = false,
    )

    fun editVisit(height: Double,
                  weight: Double, arm_circunference: Double, status: String, edema: String,
                  measlesVaccinated: Boolean, vitamineAVaccinated: Boolean,
                  treatments: List<Treatment>, complications: List<Complication>, observations: String) {
        viewModelScope.launch {
            val visit = Visit(id, _state.value.visit!!.caseId, childId, _state.value.visit!!.tutorId, Date(), height, weight, 0.0,
                arm_circunference, status, edema, measlesVaccinated, vitamineAVaccinated,
                treatments.filter {it.selected}.toMutableList(), complications.filter {it.selected}.toMutableList(),
                observations, "")
            _state.value= UiState(visit = visit)
            FirebaseDataSource.updateVisit(visit)
            _state.value = UiState(editVisit = true)
        }
    }

    fun checkDesnutrition(height: String, weight: String) {
        viewModelScope.launch {
            if (height.isNotEmpty() && weight.isNotEmpty()) {
                try {
                    _state.value= UiState(
                        visit = _state.value.visit,
                        childDateMillis = _state.value.childDateMillis,
                        treatments = _state.value.treatments,
                        complications = _state.value.complications,
                        imc = FirebaseDataSource.checkDesnutrition(
                            height.toDouble(),
                            weight.toDouble()
                        )
                    )
                } catch (error: Error) {
                    println("error: ${error}")
                }
            }
        }
    }

}
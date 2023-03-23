package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaronat1.hackaton.ui.navigation.NavArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource
import org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.CasesViewModel
import java.util.*

class TutorEditViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val id = savedStateHandle.get<String>(NavArg.ItemId.key) ?: "0"

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(tutor = FirebaseDataSource.getTutor(id))
        }
    }


    data class  UiState(
        val loading: Boolean = false,
        val tutor: Tutor? = null,
        val editTutor: Boolean = false,
        var imc: Double? = 0.0,
    )

    fun editTutor(id: String, name: String, surnames: String, address: String, phone: String,
                    birthdate: Date, ethnician: String, sex: String, childMinor: String,
                  pregnang: String, weeks: String, height: Double, weight: Double, status: String,
                  observations: String) {
        viewModelScope.launch {
            _state.value = UiState(loading = true, imc = _state.value.imc)
            val tutor = Tutor(id,
                name, surnames, sex, ethnician, birthdate, phone, address,
                Date(), Date(), childMinor, pregnang, observations, weeks, height, weight, status,true, "")
            _state.value= UiState(tutor = tutor, loading = true, imc = _state.value.imc)
            FirebaseDataSource.updateTutor(tutor)
            _state.value = UiState(editTutor = true, loading = true, imc = _state.value.imc)
        }
    }

    fun checkDesnutrition(height: String, weight: String) {
        viewModelScope.launch {
            if (height.isNotEmpty() && weight.isNotEmpty()) {
                try {
                    _state.value= UiState(
                        tutor = _state.value.tutor,
                        imc = FirebaseDataSource.checkAdultDesnutrition(
                            height.toDouble(),
                            weight.toDouble()
                        ),
                    )
                } catch (error: Error) {
                    println("error: ${error}")
                }
            }
        }
    }

}
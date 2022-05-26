package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.create

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
import org.sic4change.nut4healthcentrotratamiento.ui.screens.login.LoginViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.TutorsViewModel
import org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.TutorDetailViewModel
import java.util.*

class TutorCreateViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val id = savedStateHandle.get<String>(NavArg.ItemId.key) ?: "0"

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(phone = id)
        }
    }

    data class  UiState(
        val phone: String = "",
        val loading: Boolean = false,
        val tutor: Tutor? = null,
        val created: Boolean = false,
    )

    fun createTutor(name: String, surnames: String, address: String, phone: String,
                    birthdate: Date, ethnician: String, sex: String,  childMinor: String,
                    pregnang: String, weks: String, height: Double, weight: Double,
                    observations: String) {
        viewModelScope.launch {
            val tutor = Tutor(phone,
                name, surnames, sex, ethnician, birthdate, phone, address,
                Date(), Date(), childMinor, pregnang, observations, weks, height, weight, true)
            _state.value= UiState(tutor = tutor)
            FirebaseDataSource.createTutor(tutor)
            _state.value = UiState(created = true)
        }
    }

}
package org.sic4change.nut4healthcentrotratamiento.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.entitities.User
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource
import java.util.*

class MainViewModel() : ViewModel() {


    private var _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(user = FirebaseDataSource.getLoggedUser())
        }
    }

    data class  UiState(
        val loading: Boolean = false,
        val user: User? = null,
        val logout: Boolean = false,
        val exit: Boolean = false,
        val tutor: Tutor? = null,
        val tutorChecked: String = ""
    )

    fun logout() {
        viewModelScope.launch {
            FirebaseDataSource.logout()
            _state.value = UiState(user = null)
            _state.value = UiState(logout = true)
        }
    }

    fun checkTutorByPhone(phone: String) {
        viewModelScope.launch {
            FirebaseDataSource.checkDiagnosis(phone)
            val tutor = FirebaseDataSource.checkTutorByPhone(phone)
            if (tutor != null) {
                _state.value = UiState(tutor = tutor, tutorChecked = "found")
            } else {
                _state.value = UiState(tutor = Tutor("", "", "", "", "",
                    Date(), phone, "", Date(), Date(), "", "", "",
                    "", 0.0, 0.0, false ), tutorChecked = "not_found")
            }
        }
    }

    fun resetTutor() {
        _state.value = UiState(tutor = null)
    }

}
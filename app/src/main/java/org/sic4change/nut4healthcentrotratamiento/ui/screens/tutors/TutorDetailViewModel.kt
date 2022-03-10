package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.aaronat1.hackaton.ui.navigation.NavArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource
import org.sic4change.nut4healthcentrotratamiento.ui.screens.login.LoginViewModel

class TutorDetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

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
        val updateTutor: Boolean = false,
    )

    fun updateTutor(tutor: Tutor) {
        print("Aqui")
        viewModelScope.launch {
            FirebaseDataSource.updateTutor(tutor)
        }
    }
}
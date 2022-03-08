package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource

class TutorsViewModel: ViewModel() {

    private var _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(tutors = FirebaseDataSource.getTutors())
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val tutors: List<Tutor> = emptyList()
    )
}
package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource
import java.util.*

class TutorsViewModel: ViewModel() {

    private var _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(tutors = FirebaseDataSource.getTutors(), loading = false)
        }
    }

    fun searchTutor(text: String) {
        viewModelScope.launch {
            _state.value = UiState(tutors = emptyList())
            _state.value = UiState(loading = true)
            _state.value = UiState(tutors = FirebaseDataSource.getTutors(), loading = false)
            if (text.isNotEmpty() ) {
                _state.value = UiState(tutors = _state.value.tutors.filter {
                    (it.name + it.surnames).lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault()))
                                                                           }, loading = false)
            }

        }
    }

    fun deleteTutor(id: String) {
        viewModelScope.launch {
            FirebaseDataSource.deleteTutor(id)
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val tutors: List<Tutor> = emptyList()
    )

}
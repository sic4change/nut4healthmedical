package org.sic4change.nut4healthcentrotratamiento.ui.screens.tutors.detail.fefa

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.NavArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.STATUS
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource
import java.util.Date

class FEFAViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val id = savedStateHandle.get<String>(NavArg.ItemId.key) ?: "0"

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = _state.value.copy(tutor = FirebaseDataSource.getTutor(id))
            _state.value = _state.value.copy(cases = FirebaseDataSource.getFEFACases(id))
            _state.value = _state.value.copy(isOneCaseOpen = isOneCaseOpen())
            _state.value = _state.value.copy(loading = false)
        }
    }

    private fun isOneCaseOpen(): Boolean {
        return _state.value.cases!!.any {
            it.status == STATUS.OPEN_STATUS_VALUES[0]  ||
            it.status == STATUS.OPEN_STATUS_VALUES[1]  ||
            it.status == STATUS.OPEN_STATUS_VALUES[2]
        }
    }

    data class  UiState(
        val loading: Boolean = false,
        val tutor: Tutor? = null,
        val cases: List<Case>? = null,
        val isOneCaseOpen: Boolean = false,
        val updateTutor: Boolean = false,
        val newCase: Case? = null,
        val newCaseCreated: Boolean = false,
    )

    fun createCase(name: String, status: String, observations: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val case = Case(_state.value.tutor!!.id, null,
                _state.value.tutor!!.id, _state.value.tutor!!.id,
                name, status, Date(), Date(), "0", observations, "")
            val newCase = FirebaseDataSource.createCase(case)
            _state.value = _state.value.copy(newCase = newCase)
            _state.value = _state.value.copy(newCaseCreated = true)
            _state.value = _state.value.copy(loading = true)
        }
    }

    fun deleteTutor(id: String) {
        viewModelScope.launch {
            FirebaseDataSource.deleteTutor(id)
        }
    }

    fun deleteCase(id: String) {
        viewModelScope.launch {
            FirebaseDataSource.deleteCase(id)
        }
    }

}
package org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.NavArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource

class CaseDetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val id = savedStateHandle.get<String>(NavArg.ItemId.key) ?: "0"

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            val case = FirebaseDataSource.getCase(id)
            val childId = case!!.childId
            if (childId != null) {
                val child = FirebaseDataSource.getChild(childId)
                _state.value = _state.value.copy(child = child)
                _state.value = _state.value.copy(visits = FirebaseDataSource.getVisits(case.id))
            }
            if (case.fefaId != null) {
                _state.value = _state.value.copy(fefa = FirebaseDataSource.getTutor(case.fefaId))
                _state.value = _state.value.copy(visits = FirebaseDataSource.getVisits(case.id))
            }
            _state.value = _state.value.copy(case = case)
            _state.value = _state.value.copy(loading = false)
        }
    }

    data class  UiState(
        val loading: Boolean = false,
        val case: Case? = null,
        val fefa: Tutor? = null,
        val child: Child? = null,
        val visits : List<Visit>? = null,
        val updateCase: Boolean = false,
    )

    fun deleteCase(id: String) {
        viewModelScope.launch {
            FirebaseDataSource.deleteCase(id)
        }
    }


}
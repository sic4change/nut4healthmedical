package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaronat1.hackaton.ui.navigation.NavArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource
import org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.create.VisitCreateViewModel

class VisitDetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val id = savedStateHandle.get<String>(NavArg.ItemId.key) ?: "0"

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(visit = FirebaseDataSource.getVisit(id), loading = false)

            _state.value = UiState(
                visit = _state.value.visit,
                case = FirebaseDataSource.getCase(_state.value.visit!!.caseId),
                visits = FirebaseDataSource.getVisits(_state.value.visit!!.caseId)
            )
            _state.value = UiState(
                visit = _state.value.visit,
                case = _state.value.case,
                visits = _state.value.visits,
                childDateMillis = _state.value.case?.let { FirebaseDataSource.getChild(it.childId)?.birthdate?.time }
            )
        }
    }

    data class  UiState(
        val loading: Boolean = false,
        val visit: Visit? = null,
        val case: Case? = null,
        val visits: List<Visit> = emptyList(),
        val childDateMillis: Long? = 0,
        val updateCase: Boolean = false,
    )

    fun deleteVisit(id: String) {
        viewModelScope.launch {
            FirebaseDataSource.deleteVisit(id)
        }
    }

}
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
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Point
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
            _state.value = _state.value.copy(visit = FirebaseDataSource.getVisit(id), loading = false)

            _state.value = _state.value.copy(
                case = FirebaseDataSource.getCase(_state.value.visit!!.caseId),
                visits = FirebaseDataSource.getVisits(_state.value.visit!!.caseId)
            )
            if (_state.value.case?.point != null) {
                _state.value = _state.value.copy(point = FirebaseDataSource.getPoint(_state.value.case!!.point))
            }
            _state.value = _state.value.copy(
                child = FirebaseDataSource.getChild(_state.value.case!!.childId),
            )
            _state.value = _state.value.copy(
                childDateMillis = _state.value.case?.let { FirebaseDataSource.getChild(it.childId)?.birthdate?.time }
            )
        }
    }

    data class  UiState(
        val loading: Boolean = false,
        val point: Point? = null,
        val child: Child? = null,
        val visit: Visit? = null,
        val case: Case? = null,
        val visits: List<Visit> = emptyList(),
        val childDateMillis: Long? = 0,
        val updateCase: Boolean = false,
    )

    fun getVisitNumber(): Int {
        return state.value.visits.reversed().indexOfFirst { _state.value.visit!!.id == it.id } + 1
    }

    fun deleteVisit(id: String) {
        viewModelScope.launch {
            FirebaseDataSource.deleteVisit(id)
        }
    }

}
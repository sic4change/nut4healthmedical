package org.sic4change.nut4healthcentrotratamiento.ui.screens.visits.create

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaronat1.hackaton.ui.navigation.NavArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource
import java.util.*

class VisitCreateViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val caseId = savedStateHandle.get<String>(NavArg.ItemId.key) ?: "0"

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
        }
    }

    data class  UiState(
        val loading: Boolean = false,
        val visit: Visit? = null,
        val created: Boolean = false,
    )

    fun createVisit(height: Double, weight: Double, observations: String) {
        viewModelScope.launch {
            val visit = Visit("", caseId, caseId, caseId, Date(), height, weight, 0.0,
                0.0, "", false, false,
            emptyList(), emptyList(), observations)
            _state.value= UiState(visit = visit)
            FirebaseDataSource.createVisit(visit)
            _state.value = UiState(created = true)
        }
    }




}
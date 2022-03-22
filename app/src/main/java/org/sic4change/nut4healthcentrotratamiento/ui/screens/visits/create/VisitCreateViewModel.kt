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
import java.util.*

class VisitCreateViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val visitId = savedStateHandle.get<String>(NavArg.ItemId.key) ?: "0"

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


    fun createVisit(name: String, status: String, observations: String) {
        viewModelScope.launch {
            /*val case = Case(childId, childId, childId, name, status,
                Date(), Date(), "0", observations)
            _state.value= UiState(case = case)
            FirebaseDataSource.createCase(case)
            _state.value = UiState(created = true)*/
        }
    }



}
package org.sic4change.nut4healthcentrotratamiento.ui.screens.cases

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.NavArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource

class CasesViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val id = savedStateHandle.get<String>(NavArg.ItemId.key) ?: "0"

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(createCase = false)
            _state.value = UiState(loading = true)
            _state.value = UiState(cases = FirebaseDataSource.getChildCases(id), childId = id, createCase = false, loading = false)
        }
    }


    data class  UiState(
        val loading: Boolean = false,
        val childId: String = "",
        val cases: List<Case> = emptyList(),
        val createCase: Boolean = false,
    )

}
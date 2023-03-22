package org.sic4change.nut4healthcentrotratamiento.ui.screens.cuadrante

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Cuadrant
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource

class CuadrantsViewModel() : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(cuadrants = FirebaseDataSource.getActiveCases(), loading = false)
        }
    }


    data class  UiState(
        val loading: Boolean = false,
        val cuadrants: List<Cuadrant> = emptyList(),
    )

}
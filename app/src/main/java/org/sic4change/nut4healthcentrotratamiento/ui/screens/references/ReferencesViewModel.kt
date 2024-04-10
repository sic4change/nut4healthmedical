package org.sic4change.nut4healthcentrotratamiento.ui.screens.references

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Derivation
import org.sic4change.nut4healthcentrotratamiento.data.entitities.User
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource

class ReferencesViewModel() : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = _state.value.copy(user = FirebaseDataSource.getLoggedUser(), loading = true)
            _state.value = _state.value.copy(references = FirebaseDataSource.getReferences(_state.value.user!!.point!!).filter { it.type == "Referred" }, loading = false)
        }
    }

    fun searchReference(text: String) {
        viewModelScope.launch {

        }
    }

    data class  UiState(
        val loading: Boolean = false,
        val user: User? = null,
        val references: List<Derivation?> = emptyList(),
    )

}
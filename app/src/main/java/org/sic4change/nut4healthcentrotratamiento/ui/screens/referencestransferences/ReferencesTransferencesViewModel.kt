package org.sic4change.nut4healthcentrotratamiento.ui.screens.referencestransferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Derivation
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.entitities.User
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource

class ReferencesTransferencesViewModel() : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = _state.value.copy(user = FirebaseDataSource.getLoggedUser(), loading = true)
            _state.value = _state.value.copy(childs = FirebaseDataSource.getAllChilds(), loading = true)
            _state.value = _state.value.copy(fefas = FirebaseDataSource.getAllTutors(), loading = true)
            _state.value = _state.value.copy(referencesTransferences =
                FirebaseDataSource.getReferencesDestination(_state.value.user!!.point!!).filter { it.type == _state.value.type }, loading = false)
        }
    }

    fun searchReferenceTransference(text: String) {
        viewModelScope.launch {

        }
    }

    data class  UiState(
        var type: String = "Referred",
        val loading: Boolean = false,
        val user: User? = null,
        val childs : List<Child?> = emptyList(),
        val fefas : List<Tutor?> = emptyList(),
        val referencesTransferences: List<Derivation?> = emptyList(),
    )

}
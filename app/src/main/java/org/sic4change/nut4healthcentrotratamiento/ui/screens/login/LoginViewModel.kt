package org.sic4change.nut4healthcentrotratamiento.ui.screens.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private var _state = MutableStateFlow(UiState())

    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {

        }
    }

    data class UiState(
        val loading: Boolean = false,
        val loggedUser: Boolean = false
    )

    fun loginUser() {
        _state.value = UiState(loggedUser = true)
    }
}
package org.sic4change.nut4healthcentrotratamiento.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.User
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource

class MainViewModel() : ViewModel() {


    private var _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(user = FirebaseDataSource.getLoggedUser())
        }
    }

    data class  UiState(
        val loading: Boolean = false,
        val user: User? = null,
        val logout: Boolean = false,
        val exit: Boolean = false
    )

    fun logout() {
        viewModelScope.launch {
            FirebaseDataSource.logout()
            _state.value = UiState(user = null)
            _state.value = UiState(logout = true)
        }
    }

}
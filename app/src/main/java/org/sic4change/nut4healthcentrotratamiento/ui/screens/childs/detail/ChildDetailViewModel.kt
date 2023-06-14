package org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.detail

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
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource

class ChildDetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val id = savedStateHandle.get<String>(NavArg.ItemId.key) ?: "0"

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = _state.value.copy(child = FirebaseDataSource.getChild(id))
            _state.value = _state.value.copy(cases = FirebaseDataSource.getCases(id))
            _state.value = _state.value.copy(loading = false)
        }
    }

    data class  UiState(
        val loading: Boolean = false,
        val child: Child? = null,
        val cases: List<Case> = emptyList(),
        val updateTutor: Boolean = false,
    )

    fun deleteChild(id: String) {
        viewModelScope.launch {
            FirebaseDataSource.deleteChild(id)
        }
    }

}
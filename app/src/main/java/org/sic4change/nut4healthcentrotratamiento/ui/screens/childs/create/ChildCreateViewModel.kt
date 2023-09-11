package org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.create

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.NavArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource
import java.util.*

class ChildCreateViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val tutorId = savedStateHandle.get<String>(NavArg.ItemId.key) ?: "0"

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
        }
    }

    data class  UiState(
        val loading: Boolean = false,
        val child: Child? = null,
        val created: Boolean = false,
    )

    fun createChild(name: String, surnames: String, birthdate: Date, brothers: Int, ethnician: String,
                    sex: String, observations: String) {
        viewModelScope.launch {
            val child = Child(tutorId, tutorId, name, surnames, sex, ethnician, birthdate, brothers, "",
                Date(), Date(), observations, "")
            _state.value= UiState(child = child)
            FirebaseDataSource.createChild(child)
            _state.value = UiState(created = true)
        }
    }


}
package org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaronat1.hackaton.ui.navigation.NavArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.create.ChildCreateViewModel
import java.util.*

class ChildEditViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val id = savedStateHandle.get<String>(NavArg.ItemId.key) ?: "0"

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(child = FirebaseDataSource.getChild(id))
        }
    }


    data class  UiState(
        val loading: Boolean = false,
        val child: Child? = null,
        val editChild: Boolean = false,
    )

    fun editChild(id: String, tutorId: String, name: String, surnames: String,
                  birthdate: Date, ethnician: String, sex: String, observations: String) {
        viewModelScope.launch {
            val child = Child(id, tutorId, name, surnames, sex, ethnician, birthdate,
                Date(), Date(), observations)
            _state.value= UiState(child = child)
            FirebaseDataSource.updateChild(child)
            _state.value = UiState(editChild = true)
        }
    }

}
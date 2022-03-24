package org.sic4change.nut4healthcentrotratamiento.ui.screens.cases.edit

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
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource
import java.util.*

class CaseEditViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val id = savedStateHandle.get<String>(NavArg.ItemId.key) ?: "0"

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(case = FirebaseDataSource.getCase(id))
        }
    }


    data class  UiState(
        val loading: Boolean = false,
        val case: Case? = null,
        val editChild: Boolean = false,
    )

    fun editCase(id: String, name: String, status: String, observations: String) {
        viewModelScope.launch {
            val case = Case(id, _state.value.case!!.childId, _state.value.case!!.tutorId,
                name, status, Date(), Date(), _state.value.case!!.visits, observations)
            _state.value= UiState(case = case)
            FirebaseDataSource.updateCase(case)
            _state.value = UiState(editChild = true)
        }
    }

}
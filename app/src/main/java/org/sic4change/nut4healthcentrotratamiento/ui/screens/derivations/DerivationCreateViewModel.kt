package org.sic4change.nut4healthcentrotratamiento.ui.screens.derivations

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.NavArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Point
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.entitities.User
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource
import org.sic4change.nut4healthcentrotratamiento.ui.screens.childs.create.ChildCreateViewModel
import java.util.*

class DerivationCreateViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val caseId = savedStateHandle.get<String>(NavArg.ItemId.key) ?: "0"

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            val caseToUpdate = FirebaseDataSource.getCase(caseId)
            caseToUpdate?.closedReason = "Referred"
            FirebaseDataSource.updateCase(caseToUpdate!!)
            _state.value = UiState(case = FirebaseDataSource.getCase(caseId), loading = true)
            _state.value = _state.value.copy(user = FirebaseDataSource.getLoggedUser(), loading = true)
            _state.value = _state.value.copy(healthCentreUsers = FirebaseDataSource.getHealthServiceUsers(), loading = true)
            if (_state.value.case != null && _state.value.case!!.childId != null) {
                _state.value = _state.value.copy(child = FirebaseDataSource.getChild(_state.value.case!!.childId!!), loading = true)
            }
            if (_state.value.case != null && _state.value.case!!.tutorId != null) {
                _state.value = _state.value.copy(tutor = FirebaseDataSource.getTutor(_state.value.case!!.tutorId!!), loading = true)
            }
            if (_state.value.case != null && _state.value.case!!.point != null) {
                _state.value = _state.value.copy(point = FirebaseDataSource.getPoint(_state.value.case!!.point!!), loading = true)
            }
            val pointType = when (_state.value.point?.type) {
                "CRENAM", "Otro" -> "CRENAS"
                "CRENAS" -> "CRENI"
                else -> ""
            }
            _state.value = _state.value.copy(points = FirebaseDataSource.getPointsByType(pointType), loading = true)
            _state.value = _state.value.copy(loading = false)
        }
    }

    data class  UiState(
        val loading: Boolean = false,
        val user: User? = null,
        val healthCentreUsers : List<User> = emptyList(),
        val case: Case? = null,
        val child: Child? = null,
        val tutor: Tutor? = null,
        val point: Point? = null,
        val created: Boolean = false,
        val points: List<Point> = emptyList(),
    )

    fun createDerivation() {
        viewModelScope.launch {

        }
    }



}
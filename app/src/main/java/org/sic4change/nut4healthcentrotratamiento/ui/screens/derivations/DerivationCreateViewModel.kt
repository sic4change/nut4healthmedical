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
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Derivation
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Point
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.entitities.User
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource

class DerivationCreateViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val caseId = savedStateHandle.get<String>(NavArg.ItemId.key) ?: "0"
    val type = savedStateHandle.get<String>(NavArg.DerivationType.key) ?: "Referred"
    private val notResponse = savedStateHandle.get<Boolean>(NavArg.Unresponsive.key) ?: false

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            val caseToUpdate = FirebaseDataSource.getCase(caseId)
            if (!notResponse) {
                caseToUpdate?.closedReason = type
            } else {
                caseToUpdate?.closedReason = "Unresponsive"
            }
            FirebaseDataSource.updateCase(caseToUpdate!!)
            _state.value = UiState(case = FirebaseDataSource.getCase(caseId), type = type, loading = true)
            _state.value = _state.value.copy(lastVisit = FirebaseDataSource.getLastVisitInCase(caseId), loading = true)
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
                _state.value = _state.value.copy(references = FirebaseDataSource.getReferences(_state.value.point!!.id), loading = true)
                _state.value = _state.value.copy(visits = FirebaseDataSource.getVisits(_state.value.case!!.id), loading = true)
            }

            var pointType = ""
            if (type == "Referred") {
                pointType = when (_state.value.point?.type) {
                    "CRENAM", "Otro", "CRENAM-C" -> "CRENAS"
                    "CRENAS" -> "CRENI"
                    else -> ""
                }
            } else {
                pointType = when (_state.value.point?.type) {
                    "CRENAM"-> "CRENAM"
                    "Otro" -> "Otro"
                    "CRENAM-C"-> "CRENAM-C"
                    "CRENAS" -> "CRENAS"
                    "CRENI" -> "CRENI"
                    else -> ""
                }
            }

            _state.value = _state.value.copy(points = FirebaseDataSource.getPointsByType(pointType), loading = true)
            _state.value = _state.value.copy(points = _state.value.points.filter { it.id != _state.value.point?.id }, loading = true)
            _state.value = _state.value.copy(loading = false)
        }
    }

    data class  UiState(
        val type: String = "",
        val loading: Boolean = true,
        val user: User? = null,
        val healthCentreUsers : List<User> = emptyList(),
        val case: Case? = null,
        val lastVisit: Visit? = null,
        val child: Child? = null,
        val tutor: Tutor? = null,
        val point: Point? = null,
        val created: Boolean = false,
        val points: List<Point> = emptyList(),
        val references: List<Derivation> = emptyList(),
        val visits: List<Visit> = emptyList()
    )

    fun createDerivation(derivation: Derivation, closeText: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            FirebaseDataSource.createDerivation(derivation, closeText)
            _state.value = _state.value.copy(loading = false, created = true)
        }
    }



}
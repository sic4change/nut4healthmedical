package org.sic4change.nut4healthcentrotratamiento.ui.screens.referencestransferences

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Derivation
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Point
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.entitities.User
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource
import org.sic4change.nut4healthcentrotratamiento.ui.navigation.NavArg
import java.util.Date

class ReferencesTransferencesCaseDetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val id = savedStateHandle.get<String>(NavArg.ItemId.key) ?: "0"

    init {
        viewModelScope.launch {
            val derivation = FirebaseDataSource.getDerivation(id)
            _state.value = _state.value.copy(derivation = derivation, loading = true)
            if (derivation != null) {
                _state.value = _state.value.copy(case = FirebaseDataSource.getCase(derivation.caseId), loading = true)
                _state.value = _state.value.copy(lastVisit = FirebaseDataSource.getLastVisitInCase(derivation.caseId), loading = true)
                _state.value = _state.value.copy(visits = FirebaseDataSource.getVisits(derivation.caseId), loading = true)
                _state.value = _state.value.copy(origin = FirebaseDataSource.getPoint(derivation.originId), loading = true)
                if (derivation.childId != null) {
                    _state.value = _state.value.copy(child = FirebaseDataSource.getChild(derivation.childId), loading = true)
                }
                if (derivation.fefaId != null) {
                    _state.value = _state.value.copy(fefa = FirebaseDataSource.getTutor(derivation.fefaId), loading = true)
                }
            }
            _state.value = _state.value.copy(loading = false)

        }
    }

    fun createVisit(derivation: Derivation, caseName: String, caseStatus: String, admissionType: String ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            println("Aqui seteando derivation completed")
            FirebaseDataSource.setDerivationCompleted(derivation)
            val child = _state.value.child
            val tutor = _state.value.fefa
            if (child != null) {
                child.point = derivation.destinationId
                FirebaseDataSource.updateChild(child)
            }
            if (tutor != null) {
                tutor.point = derivation.destinationId
                FirebaseDataSource.updateTutor(tutor)
            }
            tutor?.let {
                val childId = child?.id
                val fefaId = if (child == null) it.id else null
                val tutorId = it.id

                val case = Case(
                    id = "",
                    childId = childId,
                    fefaId = fefaId,
                    tutorId = tutorId,
                    name = caseName,
                    admissionType = admissionType,
                    admissionTypeServer = admissionType,
                    status = caseStatus,
                    closedReason = "",
                    createdate = Date(),
                    lastdate = Date(),
                    visits = "0",
                    observations = "",
                    point = derivation.destinationId
                )
                _state.value = _state.value.copy(caseCreated = FirebaseDataSource.createCase(case))
            } ?: run {
                println("Error: Tutor not null")
            }

            _state.value = _state.value.copy(visitCreated = true)
            _state.value = _state.value.copy(loading = false)

        }

    }


    data class  UiState(
        val loading: Boolean = true,
        val derivation: Derivation? = null,
        val origin: Point? = null,
        val fefa: Tutor? = null,
        val child : Child? = null,
        val case: Case? = null,
        val lastVisit: Visit? = null,
        val visits: List<Visit> = emptyList(),
        val caseCreated: Case? = null,
        val visitCreated: Boolean = false
    )

}
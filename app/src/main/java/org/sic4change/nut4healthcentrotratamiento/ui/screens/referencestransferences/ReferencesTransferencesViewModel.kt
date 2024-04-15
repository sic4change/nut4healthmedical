package org.sic4change.nut4healthcentrotratamiento.ui.screens.referencestransferences

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
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource

class ReferencesTransferencesViewModel() : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = _state.value.copy(user = FirebaseDataSource.getLoggedUser(), loading = true)
            _state.value = _state.value.copy(currentPoint = FirebaseDataSource.getPoint(_state.value.user!!.point), loading = true)
            _state.value = _state.value.copy(childs = FirebaseDataSource.getAllChilds(), loading = true)
            _state.value = _state.value.copy(fefas = FirebaseDataSource.getAllTutors(), loading = true)
            var points = FirebaseDataSource.getAllPoints()
            println("Aqui1 ${points.size}")
            if (_state.value.type == "Referred" && _state.value.currentPoint!!.type == "CRENAS") {
                points = points.filter { it.type == "CRENAM" || it.type == "Otro" }
            } else if (_state.value.type == "Referred" && _state.value.currentPoint!!.type == "CRENI") {
                points = points.filter { it.type == "CRENAS"}
            } else if (_state.value.type == "Transfered" && _state.value.currentPoint!!.type == "CRENAS") {
                points = points.filter { it.type == "CRENAS"}
            } else if (_state.value.type == "Transfered" && (_state.value.currentPoint!!.type == "CRENAM"
                        || _state.value.currentPoint!!.type == "Otro")) {
                points = points.filter { it.type == "CRENAM" || it.type == "Otro" }
                println("Aqui11 ${points.size}")
                println("Aqui12 ${points.filter { it.type == "CRENAM" || it.type == "Otro" }.size}")
            } else if (_state.value.type == "Transfered" && _state.value.currentPoint!!.type == "CRENI") {
                points = points.filter { it.type == "CRENI"}
            }
            println("Aqui2 ${points.size}")
            _state.value = _state.value.copy(points = points, loading = true)
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
        val currentPoint: Point? = null,
        val points: List<Point?> = emptyList(),
        val referencesTransferences: List<Derivation?> = emptyList(),
    )

}
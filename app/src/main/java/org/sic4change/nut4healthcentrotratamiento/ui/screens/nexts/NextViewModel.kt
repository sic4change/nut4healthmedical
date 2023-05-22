package org.sic4change.nut4healthcentrotratamiento.ui.screens.nexts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Cuadrant
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Point
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.entitities.User
import org.sic4change.nut4healthcentrotratamiento.data.network.FirebaseDataSource
import org.sic4change.nut4healthcentrotratamiento.ui.screens.main.MainViewModel
import java.util.*

class NextsViewModel() : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            filterNext(0)
            _state.value = UiState(user = FirebaseDataSource.getLoggedUser())
        }
    }

    fun getPoint(pointId: String?) {
        viewModelScope.launch {
            _state.value = UiState(point = FirebaseDataSource.getPoint(pointId))
        }
    }

    fun checkTutorByPhone(phone: String) {
        viewModelScope.launch {
            val tutor = FirebaseDataSource.checkTutorByPhone(phone)
            if (tutor != null) {
                _state.value = UiState(tutor = tutor, tutorChecked = "found")
            } else {
                _state.value = UiState(
                    tutor = Tutor(
                        "", "", "", "", "",
                        Date(), phone, "", Date(), Date(), "", "", "", "",
                        0.0, "", "", "", false, ""
                    ), tutorChecked = "not_found"
                )
            }
        }
    }

    fun resetTutor() {
        _state.value = UiState(tutor = null)
    }

    fun filterNext(value: Int) {
        viewModelScope.launch {
            _state.value = UiState(cuadrants = emptyList())
            _state.value = UiState(loading = true)
            _state.value = UiState(cuadrants = FirebaseDataSource.getActiveCases().filterNotNull(), loading = false)
            if (value == 0) {
                val todayVisits = _state.value.cuadrants.filter { isSameDay(Date(it!!.createdate.time + (14 * 24 * 60 * 60 * 1000)), Date()) }
                if (todayVisits != null) {
                    _state.value = UiState(cuadrants = todayVisits, loading = false)
                } else {
                    _state.value = UiState(cuadrants = emptyList(), loading = false)
                }

            } else if (value == 1) {
                val weekVisits = _state.value.cuadrants.filter { isSameWeek(Date(it!!.createdate.time + (14 * 24 * 60 * 60 * 1000)), Date()) }
                if (weekVisits != null) {
                    _state.value = UiState(cuadrants = weekVisits, loading = false)
                } else {
                    _state.value = UiState(cuadrants = emptyList(), loading = false)
                }
            } else {
                val monthVisits = _state.value.cuadrants.filter { isSameMonth(Date(it!!.createdate.time + (14 * 24 * 60 * 60 * 1000)), Date()) }
                if (monthVisits != null) {
                    _state.value = UiState(cuadrants = monthVisits, loading = false)
                } else {
                    _state.value = UiState(cuadrants = emptyList(), loading = false)
                }
            }
        }
    }

    private fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance()
        cal1.time = date1

        val cal2 = Calendar.getInstance()
        cal2.time = date2

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
    }

    fun isSameWeek(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance()
        cal1.time = date1

        val cal2 = Calendar.getInstance()
        cal2.time = date2

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)
    }

    private fun isSameMonth(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance()
        cal1.time = date1

        val cal2 = Calendar.getInstance()
        cal2.time = date2

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
    }

    data class  UiState(
        val loading: Boolean = false,
        val cuadrants: List<Cuadrant?> = emptyList(),
        val user: User? = null,
        val point: Point? = null,
        val tutor: Tutor? = null,
        val tutorChecked: String = "",
    )

}
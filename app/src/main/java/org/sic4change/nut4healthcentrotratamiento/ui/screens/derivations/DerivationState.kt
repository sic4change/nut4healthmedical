package org.sic4change.nut4healthcentrotratamiento.ui.screens.derivations

import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import org.sic4change.nut4healthcentrotratamiento.R
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Point
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.entitities.User
import org.sic4change.nut4healthcentrotratamiento.ui.commons.getMonths
import org.sic4change.nut4healthcentrotratamiento.ui.commons.getYears
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale


@Composable
fun rememberDerivationState(
    id: MutableState<String> = rememberSaveable { mutableStateOf("") },
    caseId:  MutableState<String> = rememberSaveable { mutableStateOf("") },
    tutor: MutableState<Tutor?> = rememberSaveable { mutableStateOf(null) },
    child: MutableState<Child?> = rememberSaveable { mutableStateOf(null) },
    pointId: MutableState<String> = rememberSaveable { mutableStateOf("") },
    currentPointName: MutableState<String> = rememberSaveable { mutableStateOf("") },
    currentPointPhone: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedDerivationCentre: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedOptionDerivationCentre: MutableState<String> = rememberSaveable { mutableStateOf("") },
    points: MutableState<MutableList<Point>> = rememberSaveable {mutableStateOf(mutableListOf<Point>())},
    healthCentres: MutableState<MutableList<User>> = rememberSaveable {mutableStateOf(mutableListOf<User>())},
) = remember{ DerivationState(id, caseId, tutor, child, pointId, currentPointName, currentPointPhone,
    expandedDerivationCentre, selectedOptionDerivationCentre, points, healthCentres) }

class DerivationState(
    val id: MutableState<String>,
    val caseId: MutableState<String>,
    val tutor: MutableState<Tutor?>,
    val child: MutableState<Child?>,
    val pointId: MutableState<String>,
    val currentPointName: MutableState<String>,
    val currentPointPhone: MutableState<String>,
    val expandedDerivationCentre: MutableState<Boolean>,
    val selectedOptionDerivationCentre: MutableState<String>,
    val points: MutableState<MutableList<Point>>,
    val healthCentres: MutableState<MutableList<User>>
) {

    fun getPhoneSelectedDerivationCentre(): String {
        if (selectedOptionDerivationCentre.value.isEmpty()) {
            return ""
        } else {
            val selectedPoint = points.value.find { it.name == selectedOptionDerivationCentre.value }
            var phones = ""
            healthCentres.value.forEach { user ->
                if (user.point == selectedPoint?.id) {
                    phones += user.phone + " / "
                }
            }
            if (phones.isNotEmpty()) {
                phones = phones.substring(0, phones.length - 3)
            }
            return phones
        }
    }

    fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(calendar.time)
    }

}

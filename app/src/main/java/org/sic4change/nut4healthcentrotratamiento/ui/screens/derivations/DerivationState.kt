package org.sic4change.nut4healthcentrotratamiento.ui.screens.derivations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Point
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.entitities.User
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun rememberDerivationState(
    id: MutableState<String> = rememberSaveable { mutableStateOf("") },
    caseId:  MutableState<String> = rememberSaveable { mutableStateOf("") },
    case:  MutableState<Case?> = rememberSaveable { mutableStateOf(null) },
    lastVisit:  MutableState<Visit?> = rememberSaveable { mutableStateOf(null) },
    tutor: MutableState<Tutor?> = rememberSaveable { mutableStateOf(null) },
    child: MutableState<Child?> = rememberSaveable { mutableStateOf(null) },
    pointId: MutableState<String> = rememberSaveable { mutableStateOf("") },
    currentPointName: MutableState<String> = rememberSaveable { mutableStateOf("") },
    currentPointPhone: MutableState<String> = rememberSaveable { mutableStateOf("") },
    expandedDerivationCentre: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedOptionDerivationCentre: MutableState<String> = rememberSaveable { mutableStateOf("") },
    points: MutableState<MutableList<Point>> = rememberSaveable {mutableStateOf(mutableListOf<Point>())},
    healthCentres: MutableState<MutableList<User>> = rememberSaveable {mutableStateOf(mutableListOf<User>())},
) = remember{ DerivationState(id, caseId, case, lastVisit, tutor, child, pointId, currentPointName, currentPointPhone,
    expandedDerivationCentre, selectedOptionDerivationCentre, points, healthCentres) }

class DerivationState(
    val id: MutableState<String>,
    val caseId: MutableState<String>,
    val case: MutableState<Case?>,
    val lastVisit: MutableState<Visit?>,
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

    fun getAdmissionDate(): String {
        val date = case.value!!.createdate
        val locale = Locale("es", "ES")
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", locale)
        return dateFormat.format(date)
    }

    fun getMASCode() : String {
        if (selectedOptionDerivationCentre.value.isEmpty()) {
            return ""
        } else {
            val selectedPoint = points.value.find { it.name == selectedOptionDerivationCentre.value }
            return selectedPoint?.pointCode + "/"
        }
    }

    fun getWeight() : String {
        var weight = "--"
        if (lastVisit.value != null) {
            weight = lastVisit.value!!.weight.toString()
        }
        return weight
    }

    fun getHeight() : String {
        var height = "--"
        if (lastVisit.value != null) {
            height = lastVisit.value!!.height.toString()
        }
        return height
    }

    fun getIMC() : String {
        var imc = "--"
        if (lastVisit.value != null) {
            imc = lastVisit.value!!.imc.toString()
        }
        return imc
    }

    fun getArmCircunference() : String {
        var armCircunference = "--"
        if (lastVisit.value != null) {
            armCircunference = lastVisit.value!!.armCircunference.toString()
        }
        return armCircunference
    }

    fun getComplications(): String {
        var complications = ""
        if (lastVisit.value != null) {
            lastVisit.value!!.complications.forEach { complication ->
                complications += complication.name + " / "
            }
            if (complications.isNotEmpty()) {
                complications = complications.substring(0, complications.length - 3)
            }
        }
        return complications
    }

}

package org.sic4change.nut4healthcentrotratamiento.ui.screens.referencestransferences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Case
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Child
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Complication
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Derivation
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Point
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun rememberReferencesTransferencesState(
    referencesTransferences:  MutableState<List<Derivation?>> = rememberSaveable { mutableStateOf(emptyList()) },
    tutors:  MutableState<List<Tutor?>> = rememberSaveable { mutableStateOf(emptyList()) },
    childs:  MutableState<List<Child?>> = rememberSaveable { mutableStateOf(emptyList()) },
    expanded: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    expandedOrigin: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    origins: MutableState<List<String?>> = rememberSaveable { mutableStateOf(emptyList()) },
    originName: MutableState<String> = rememberSaveable { mutableStateOf("") },
    codeNumber: MutableState<String> = rememberSaveable { mutableStateOf("00000") },
    expandedYear: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    year: MutableState<String> = rememberSaveable { mutableStateOf(getCurrentYear()) },
    expandedDetail: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    derivation: MutableState<Derivation> = rememberSaveable { mutableStateOf(Derivation("", "", "", "", "", "", "", Date(), "", false)) },
    case: MutableState<Case> = rememberSaveable { mutableStateOf(Case("", "", "", "", "", "", "", "", "", Date(), Date(), "", "", "")) },
    lastVisit: MutableState<Visit> = rememberSaveable { mutableStateOf(Visit("", "","" ,"", "" ,Date(), 0.0, 0.0, 0.0, 0.0, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
        emptyList<Complication>().toMutableList(), "", "")) },
    visits: MutableState<List<Visit>> = rememberSaveable { mutableStateOf(emptyList()) },
    child: MutableState<Child> = rememberSaveable { mutableStateOf(Child("", "", "", "", "", "", Date(), 0, "", Date(), Date(), "", "")) },
    fefa: MutableState<Tutor> = rememberSaveable { mutableStateOf(Tutor("", "", "", "", "", Date(), "", "", Date(), Date(), "", "", "", "", "", "", false, "")) },
    point: MutableState<Point> = rememberSaveable { mutableStateOf(Point("", "" , "", "", "", "", "", 0, "")) },
    caseCreated: MutableState<Case> = rememberSaveable { mutableStateOf(Case("", "", "", "", "", "", "", "", "", Date(), Date(), "", "", "")) },
    visitCreated: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
) = remember{ ReferencesTransferencesState(referencesTransferences, tutors, childs, expanded,
    origins, expandedOrigin, originName, codeNumber, expandedYear, year, expandedDetail,
    derivation, case, lastVisit, visits, child, fefa, point, caseCreated, visitCreated) }


fun getCurrentYear(): String {
    val calendar = Calendar.getInstance()
    return calendar.get(Calendar.YEAR).toString()
}

class ReferencesTransferencesState(
    val referencesTransferences: MutableState<List<Derivation?>>,
    val tutors: MutableState<List<Tutor?>>,
    val childs: MutableState<List<Child?>>,
    val expanded: MutableState<Boolean>,
    val origins: MutableState<List<String?>>,
    val expandedOrigin: MutableState<Boolean>,
    val originName: MutableState<String>,
    val codeNumber: MutableState<String>,
    val expandedYear: MutableState<Boolean>,
    val year: MutableState<String>,

    val expandedDetail: MutableState<Boolean>,
    val derivation: MutableState<Derivation>,
    val case: MutableState<Case>,
    val lastVisit: MutableState<Visit>,
    val visits: MutableState<List<Visit>>,
    val child: MutableState<Child>,
    val fefa: MutableState<Tutor>,
    val origin: MutableState<Point>,
    val caseCreated: MutableState<Case>,
    val visitCreated: MutableState<Boolean>,
    ) {

    fun expandContractDetail() {
        expandedDetail.value = !expandedDetail.value
    }

    fun enableSarch() : Boolean = codeNumber.value.length >= 5 && originName.value.isNotEmpty()

    fun getYears(): List<String> {
        val years = mutableListOf<String>()
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        for (i in 0..2) {
            val year = (currentYear - i)
            years.add(year.toString())
        }
        return years
    }

    fun getReferenceTransferenceFound(items: List<Derivation?>): Derivation? {
        val codePrefix = "${originName.value}/${codeNumber.value}/${year.value.substring(2, 4)}/"

        items.forEach { derivation ->
            derivation?.let {
                if (it.code.startsWith(codePrefix)) {
                    return it
                }
            }
        }
        return null
    }




}

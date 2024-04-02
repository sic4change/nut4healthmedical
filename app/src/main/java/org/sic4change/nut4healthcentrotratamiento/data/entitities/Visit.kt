package org.sic4change.nut4healthcentrotratamiento.data.entitities

import java.io.Serializable
import java.util.*

data class Visit(
    override var id: String,
    val caseId: String,
    val childId: String?,
    val fefaId: String?,
    val tutorId: String,
    val createdate: Date,
    val height: Double,
    var weight: Double,
    var imc: Double,
    val armCircunference: Double,
    var status: String,
    val edema: String,
    val respiratonStatus: String,
    val appetiteTest: String,
    val infection: String,
    val eyesDeficiency: String,
    val deshidratation: String,
    val vomiting: String,
    val diarrhea: String,
    val fever: String,
    val cough: String,
    val temperature: String,
    val vitamineAVaccinated: String,
    val acidfolicAndFerroVaccinated: String,
    val vaccinationCard: String,
    val rubeolaVaccinated: String,
    val amoxicilina: String,
    val otherTratments: String,
    val complications: MutableList<Complication>,
    var observations: String,
    var point: String?,
    ) : Item, Serializable

package org.sic4change.nut4healthcentrotratamiento.data.entitities

import java.util.*

data class Visit(
    override val id: String,
    val caseId: String,
    val childId: String,
    val tutorId: String,
    val createdate: Date,
    val height: Int,
    var weight: Double,
    var imc: Double,
    val armCircunference: Double,
    var status: String,
    val measlesVaccinated: Boolean,
    val vitamineAVaccinated: Boolean,
    val symtoms: List<String>,
    val treatments: List<String>,
    var observations: String,
    ) : Item

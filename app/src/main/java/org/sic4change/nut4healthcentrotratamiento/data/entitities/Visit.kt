package org.sic4change.nut4healthcentrotratamiento.data.entitities

import java.util.*

data class Visit(
    override val id: String,
    val caseId: String,
    val childId: String,
    val tutorId: String,
    val createdate: Date,
    val height: Double,
    var weight: Double,
    var imc: Double,
    val armCircunference: Double,
    var status: String,
    val measlesVaccinated: Boolean,
    val vitamineAVaccinated: Boolean,
    val symtoms: MutableList<Symtom>,
    val treatments: MutableList<Treatment>,
    var observations: String,
    ) : Item

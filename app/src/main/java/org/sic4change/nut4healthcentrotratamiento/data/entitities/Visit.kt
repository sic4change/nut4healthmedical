package org.sic4change.nut4healthcentrotratamiento.data.entitities

import java.util.*

data class Visit(
    override val id: String,
    val caseId: String,
    val childId: String,
    val tutorId: String,
    val createdate: Date,
    val height: Int,
    var weight: Int,
    var imc: Double,
    val armCircunference: Double,
    var status: String,
    val symtoms: List<Symtom>,
    val treatments: List<Treatment>,
    ) : Item

package org.sic4change.nut4healthcentrotratamiento.data.entitities

import java.io.Serializable
import java.util.*

data class VisitWithoutDiagnosis(
    override var id: String,
    val childId: String?,
    val fefaId: String?,
    val tutorId: String,
    val createdate: Date,
    val height: Double,
    var weight: Double,
    var imc: Double,
    val armCircunference: Double,
    var observations: String,
    var point: String?,
    ) : Item, Serializable

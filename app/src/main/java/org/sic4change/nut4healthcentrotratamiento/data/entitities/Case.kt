package org.sic4change.nut4healthcentrotratamiento.data.entitities

import java.io.Serializable
import java.util.*

data class Case(
    override var id: String,
    val childId: String?,
    val fefaId: String?,
    val tutorId: String,
    val name: String,
    val admissionType: String,
    val admissionTypeServer: String,
    var status: String,
    var closedReason: String,
    val createdate: Date,
    val lastdate: Date,
    var visits: String,
    val observations: String,
    var point: String?,
    ) : Item, Serializable


object STATUS {
    val OPEN_STATUS_VALUES: List<String> = listOf("Abierto", "Ouvert", "مفتوح")
}
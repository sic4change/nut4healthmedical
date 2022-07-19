package org.sic4change.nut4healthcentrotratamiento.data.entitities

import java.util.*

data class Cuadrant(
    override var id: String,
    val childId: String,
    val childName: String,
    val tutorId: String,
    val tutorName: String,
    val name: String,
    var status: String,
    val createdate: Date,
    val lastdate: Date,
    val nextVisit: Date,
    val visitsCuadrant: List<Visit>,
    var visits: String,
    val observations: String,
    ) : Item

package org.sic4change.nut4healthcentrotratamiento.data.entitities

import java.util.*

data class Case(
    override var id: String,
    val childId: String,
    val tutorId: String,
    val name: String,
    var status: String,
    val createdate: Date,
    val lastdate: Date,
    var visits: String,
    val observations: String,
    ) : Item

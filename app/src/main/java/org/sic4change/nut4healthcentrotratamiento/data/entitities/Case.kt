package org.sic4change.nut4healthcentrotratamiento.data.entitities

import java.util.*

data class Case(
    override val id: String,
    val childId: String,
    val tutorId: String,
    var status: String,
    val createdate: Date,
    val lastdate: Date,
    var visits: String,
    val observations: String,
    ) : Item
package org.sic4change.nut4healthcentrotratamiento.data.entitities

import java.util.*

data class Case(
    override val id: String,
    val childId: String,
    val tutorId: String,
    var state: String,
    val createDate: Date,
    val lastDate: Date,
    var visits: String,
    val observations: String,
    ) : Item

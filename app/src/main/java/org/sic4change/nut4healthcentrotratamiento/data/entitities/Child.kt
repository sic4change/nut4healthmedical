package org.sic4change.nut4healthcentrotratamiento.data.entitities

import java.io.Serializable
import java.util.*

data class Child(
    override var id: String,
    val tutorId: String,
    var name: String,
    val surnames: String,
    val sex: String,
    val ethnicity: String,
    val birthdate: Date,
    val brothers: Int,
    var code: String,
    val createDate: Date,
    val lastDate: Date,
    val observations: String,
    var point: String?,
    ) : Item, Serializable

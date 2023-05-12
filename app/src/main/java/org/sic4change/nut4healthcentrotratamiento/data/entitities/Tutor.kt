package org.sic4change.nut4healthcentrotratamiento.data.entitities

import java.util.*

data class Tutor(
    override val id: String,
    var name: String,
    val surnames: String,
    val sex: String,
    val ethnicity: String,
    val birthdate: Date,
    val phone: String,
    val address: String,
    val createDate: Date,
    val lastDate: Date,
    val maleRelation: String,
    val childMinor: String,
    val pregnant: String,
    val observations: String,
    val weeks: String,
    val height: Double,
    val weight: Double,
    val status: String,
    val active: Boolean,
    var point: String?,
    ) : Item

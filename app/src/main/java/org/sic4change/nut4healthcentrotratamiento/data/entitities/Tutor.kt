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
    val pregnant: String,
    val observations: String,
    val fingerprint: Byte,
    val active: Boolean,
    ) : Item

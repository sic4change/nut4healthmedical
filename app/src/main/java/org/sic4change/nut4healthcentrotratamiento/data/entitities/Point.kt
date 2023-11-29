package org.sic4change.nut4healthcentrotratamiento.data.entitities

import java.util.*

data class Point(
    override var id: String,
    val name: String,
    val pointName: String,
    val pointCode: String,
    val fullName: String,
    val phoneCode: String,
    val type: String,
    val phoneLength: Int,
    ) : Item

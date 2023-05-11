package org.sic4change.nut4healthcentrotratamiento.data.entitities

import java.util.*

data class Point(
    override var id: String,
    val name: String,
    val fullName: String,
    val phoneCode: String,
    val phoneLength: Int,
    ) : Item

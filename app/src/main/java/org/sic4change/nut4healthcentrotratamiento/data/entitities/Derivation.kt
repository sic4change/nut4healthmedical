package org.sic4change.nut4healthcentrotratamiento.data.entitities

import java.util.*

data class Derivation(
    override var id: String,
    val originId: String,
    val destinationId: String,
    val childId: String?,
    val fefaId: String?,
    val createdate: Date,
    ) : Item

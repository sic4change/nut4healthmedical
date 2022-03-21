package org.sic4change.nut4healthcentrotratamiento.data.entitities

import java.util.*

data class Symtom(
    override val id: String,
    val name: String,
    val name_en: String,
    val name_fr: String,
    ) : Item

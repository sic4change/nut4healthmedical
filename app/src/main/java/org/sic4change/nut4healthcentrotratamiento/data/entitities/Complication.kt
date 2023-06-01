package org.sic4change.nut4healthcentrotratamiento.data.entitities


data class Complication(
    override val id: String,
    val name: String = "",
    val name_en: String = "",
    val name_fr: String = "",
    val active: Boolean,
    var selected: Boolean = false
    ) : Item
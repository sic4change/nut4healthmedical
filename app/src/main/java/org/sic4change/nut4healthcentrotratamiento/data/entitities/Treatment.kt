package org.sic4change.nut4healthcentrotratamiento.data.entitities


data class Treatment(
    override val id: String,
    val name: String,
    val name_en: String,
    val name_fr: String,
    val active: Boolean,
    val price: Double,
    var selected: Boolean
    ) : Item

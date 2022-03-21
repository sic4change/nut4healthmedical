package org.sic4change.nut4healthcentrotratamiento.data.entitities


data class MalNutritionChildTable(
    override val id: String,
    val cm: String,
    val minusone: String,
    val minusonefive: String,
    var minusthree: String,
    val minustwo: String,
    val zero: String,
    ) : Item

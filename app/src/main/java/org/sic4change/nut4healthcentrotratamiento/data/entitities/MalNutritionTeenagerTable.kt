package org.sic4change.nut4healthcentrotratamiento.data.entitities


data class MalNutritionTeenagerTable(
    override val id: String,
    val cm: String,
    val eighty: String,
    val eightyfive: String,
    var hundred: String,
    val seventy: String,
    val sex: String,
    ) : Item

package org.sic4change.nut4healthcentrotratamiento.data.entitities

data class User(
    override val id: String,
    val email: String,
    val role: String,
    val username: String
) : Item

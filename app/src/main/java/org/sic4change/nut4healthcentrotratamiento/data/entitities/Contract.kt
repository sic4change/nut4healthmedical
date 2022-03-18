package org.sic4change.nut4healthcentrotratamiento.data.entitities


data class Contract(
    override val id: String,
    val status: String,
    val medicalDate: String,
    val medicalDateMiliseconds: Long,
    val medicalDateToUpdate: String,
    val medicalDateToUpdateInMilis: Long,
    ) : Item



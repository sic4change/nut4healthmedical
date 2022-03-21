package org.sic4change.nut4healthcentrotratamiento.data.network

import com.google.firebase.firestore.Exclude
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class NetworkUsersContainer(val results: List<User>)

@JsonClass(generateAdapter = true)
data class User(
    @Exclude val id: String = "",
    @Exclude val email: String = "",
    @Exclude val role: String = "",
    @Exclude val username: String = "",
    @Exclude val photo: String = "")

@JsonClass(generateAdapter = true)
data class NetworkTutorsContainer(val results: List<Tutor>)

@JsonClass(generateAdapter = true)
data class Tutor(
    @Exclude val id: String = "",
    @Exclude val name: String = "",
    @Exclude val surnames: String = "",
    @Exclude val sex: String = "",
    @Exclude val ethnicity: String = "",
    @Exclude val birthdate: Date = Date(),
    @Exclude val phone: String = "",
    @Exclude val address: String = "",
    @Exclude val createDate: Date = Date(),
    @Exclude val lastDate: Date = Date(),
    @Exclude val pregnant: String = "",
    @Exclude val observations: String = "",
    @Exclude val weeks: Int = 0,
    @Exclude val active: Boolean = false
    )

@JsonClass(generateAdapter = true)
data class NetworkChildsContainer(val results: List<Child>)

@JsonClass(generateAdapter = true)
data class Child(
    @Exclude val id: String = "",
    @Exclude val tutorId: String = "",
    @Exclude val name: String = "",
    @Exclude val surnames: String = "",
    @Exclude val sex: String = "",
    @Exclude val ethnicity: String = "",
    @Exclude val birthdate: Date = Date(),
    @Exclude val createDate: Date = Date(),
    @Exclude val lastDate: Date = Date(),
    @Exclude val observations: String = "",
)

@JsonClass(generateAdapter = true)
data class NetworkCasesContainer(val results: List<Case>)

@JsonClass(generateAdapter = true)
data class Case(
    @Exclude val id: String = "",
    @Exclude val childId: String = "",
    @Exclude val tutorId: String = "",
    @Exclude val name: String = "",
    @Exclude val status: String = "",
    @Exclude val createdate: Date = Date(),
    @Exclude val lastdate: Date = Date(),
    @Exclude val visits: Int = 0,
    @Exclude val observations: String = "",
)

@JsonClass(generateAdapter = true)
data class NetworkContractContainer(val results: List<Contract>)

@JsonClass(generateAdapter = true)
data class Contract(
    @Exclude val id: String = "",
    @Exclude val status: String = "",
    @Exclude val medicalDate: String = "",
    @Exclude val medicalDateMiliseconds: Long = 0,
    @Exclude val medicalDateToUpdate: String = "",
    @Exclude val medicalDateToUpdateInMilis: Long = 0,
    )

@JsonClass(generateAdapter = true)
data class NetworkMalNutritionChildTableContainer(val results: List<MalNutritionChildTable>)

@JsonClass(generateAdapter = true)
data class MalNutritionChildTable(
    @Exclude val id: String = "",
    @Exclude val cm: String,
    @Exclude val minusone: String,
    @Exclude val minusonefive: String,
    @Exclude val minusthree: String,
    @Exclude val minustwo: String,
    @Exclude val zero: String,
)

@JsonClass(generateAdapter = true)
data class NetworkMalNutritionTeenagerTableContainer(val results: List<MalNutritionTeenagerTable>)

@JsonClass(generateAdapter = true)
data class MalNutritionTeenagerTable(
    @Exclude val id: String = "",
    @Exclude val cm: String,
    @Exclude val eighty: String,
    @Exclude val eightyfive: String,
    @Exclude val hundred: String,
    @Exclude val seventy: String,
    @Exclude val sex: String,
)




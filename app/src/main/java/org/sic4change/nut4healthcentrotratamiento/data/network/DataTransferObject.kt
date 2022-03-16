package org.sic4change.nut4healthcentrotratamiento.data.network

import com.google.firebase.firestore.Exclude
import com.squareup.moshi.JsonClass
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Item
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

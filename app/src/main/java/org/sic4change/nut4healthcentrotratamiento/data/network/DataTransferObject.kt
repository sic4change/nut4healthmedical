package org.sic4change.nut4healthcentrotratamiento.data.network

import com.google.firebase.firestore.Exclude
import com.squareup.moshi.JsonClass
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Item
import java.util.*

@JsonClass(generateAdapter = true)
data class NetworkUserContainer(val results: List<User>)

@JsonClass(generateAdapter = true)
data class User(
    @Exclude val id: String = "",
    @Exclude val email: String = "",
    @Exclude val role: String = "",
    @Exclude val username: String = "",
    @Exclude val photo: String = "")


@JsonClass(generateAdapter = true)
data class Tutor(
    @Exclude val id: String = "",
    @Exclude val name: String = "",
    @Exclude val surnames: String = "",
    @Exclude val sex: String = "",
    @Exclude val ethnicity: String = "",
    @Exclude val birthdate: Date = Date(),
    @Exclude val phone: String,
    @Exclude val address: String = "",
    @Exclude val createDate: Date = Date(),
    @Exclude val lastDate: Date = Date(),
    @Exclude val pregnant: Boolean = false,
    @Exclude val observations: String = "",
    @Exclude val fingerprint: Byte = Byte.MIN_VALUE
)
package org.sic4change.nut4healthcentrotratamiento.data.network

import com.google.firebase.firestore.Exclude
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkUserContainer(val results: List<User>)

@JsonClass(generateAdapter = true)
data class User(
    @Exclude val id: String = "",
    @Exclude val email: String = "",
    @Exclude val role: String = "",
    @Exclude val username: String = "",
    @Exclude val photo: String = "")


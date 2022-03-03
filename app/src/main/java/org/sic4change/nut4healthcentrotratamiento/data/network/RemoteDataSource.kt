package org.sic4change.data.source

import org.sic4change.nut4healthcentrotratamiento.data.entitities.User


interface RemoteDataSource {
    suspend fun isLogged(): Boolean
    suspend fun getUser(email: String) : User
    suspend fun login(email: String, password: String) : String
    suspend fun logout()
    suspend fun forgotPassword(email: String) : Boolean


}
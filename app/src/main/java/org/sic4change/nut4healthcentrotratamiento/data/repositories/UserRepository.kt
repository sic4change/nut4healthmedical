package org.sic4change.nut4healthcentrotratamiento.data.repositories

import org.sic4change.data.source.RemoteDataSource
import org.sic4change.nut4healthcentrotratamiento.data.entitities.User


class UserRepository (private val remoteDataSource: RemoteDataSource) {

    suspend fun isLogged(): Boolean {
        return remoteDataSource.isLogged()
    }

    suspend fun getUser(email: String) : User {
        return remoteDataSource.getUser(email)

    }

    suspend fun login(email: String, password: String) : String {
        val result = remoteDataSource.login(email, password)
        if (result == "logged") {
            getUser(email)
        }
        return result
    }

    suspend fun logout() {
        remoteDataSource.logout()
    }

    suspend fun forgotPassword(email: String) : Boolean = remoteDataSource.forgotPassword(email)
}
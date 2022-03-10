package org.sic4change.nut4healthcentrotratamiento.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.sic4change.nut4healthcentrotratamiento.data.*
import timber.log.Timber


object FirebaseDataSource {

    suspend fun isLogged(): Boolean = withContext(Dispatchers.IO) {
        val firestoreAuth = NUT4HealthFirebaseService.fbAuth
        firestoreAuth.currentUser != null
    }

    suspend fun getLoggedUser(): org.sic4change.nut4healthcentrotratamiento.data.entitities.User =
        withContext(Dispatchers.IO) {
            val firestoreAuth = NUT4HealthFirebaseService.fbAuth
            val firestore = NUT4HealthFirebaseService.mFirestore
            val userRef = firestore.collection("users")
            val query = userRef.whereEqualTo("email", firestoreAuth.currentUser!!.email).limit(1)
            val result = query.get().await()
            val networkUserContainer = NetworkUsersContainer(result.toObjects(User::class.java))
            networkUserContainer.results[0].let {
                it.toDomainUser()
            }
        }

    suspend fun getUser(email: String): org.sic4change.nut4healthcentrotratamiento.data.entitities.User = withContext(Dispatchers.IO) {
        val firestore = NUT4HealthFirebaseService.mFirestore
        val userRef = firestore.collection("users")
        val query = userRef.whereEqualTo("email", email).limit(1)
        val result = query.get().await()
        val networkUserContainer = NetworkUsersContainer(result.toObjects(User::class.java))
        networkUserContainer.results[0].let {
                it.toDomainUser()
        }
    }

    suspend fun login(email: String, password: String): Boolean {
        var result = false
        withContext(Dispatchers.IO) {
            try {
                val login = NUT4HealthFirebaseService.fbAuth.signInWithEmailAndPassword(email, password).await()
                val user = login.user
                if (user != null) {
                    Timber.d("Login result: ok")
                    result = true
                }
            } catch (ex: Exception) {
                Timber.d("Login result: error ${ex.message}")
                result = false
            }
        }
        return result
    }

    suspend fun logout() {
        withContext(Dispatchers.IO) {
            NUT4HealthFirebaseService.fbAuth.signOut()
        }
    }

    suspend fun forgotPassword(email: String): Boolean {
        var result = false
        withContext(Dispatchers.IO) {
            Timber.d("try to request change password with firebase")
            try {
                NUT4HealthFirebaseService.fbAuth.sendPasswordResetEmail(email).await()
                Timber.d("Request change password: ok")
                result = true
            } catch (ex : Exception) {
                Timber.d("Request change password: error ${ex.message}")
            }
        }
        return result
    }

    suspend fun getTutors(): List<org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor> = withContext(Dispatchers.IO) {
        val firestore = NUT4HealthFirebaseService.mFirestore
        val tutorsRef = firestore.collection("tutors")
        val query = tutorsRef.whereEqualTo("active", true)
        val result = query.get().await()
        val networkTutorsContainer = NetworkTutorsContainer(result.toObjects(Tutor::class.java))
        networkTutorsContainer.results.map { it.toDomainTutor() }
    }

    suspend fun getTutor(id: String): org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor = withContext(Dispatchers.IO) {
        val firestore = NUT4HealthFirebaseService.mFirestore
        val tutorsRef = firestore.collection("tutors")
        val query = tutorsRef.whereEqualTo("id", id)
        val result = query.get().await()
        val networkTutorsContainer = NetworkTutorsContainer(result.toObjects(Tutor::class.java))
        networkTutorsContainer.results[0].let {
            it.toDomainTutor()
        }
    }

    suspend fun updateTutor(tutor: org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor) {
        withContext(Dispatchers.IO) {
            Timber.d("try to update tutor from firebase")
            try {
                val firestore = NUT4HealthFirebaseService.mFirestore
                val personRef = firestore.collection("tutors")
                personRef.document(tutor.id).set(tutor).await()
                Timber.d("update tutor result: ok")
                //uploadPersonFile(personToUpdate)
            } catch (ex: Exception) {
                Timber.d("update tutor result: false ${ex.message}")
            }
        }
    }


}


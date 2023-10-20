package org.sic4change.nut4healthcentrotratamiento.data.network

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Cuadrant
import org.sic4change.nut4healthcentrotratamiento.data.toDomainCase
import org.sic4change.nut4healthcentrotratamiento.data.toDomainChild
import org.sic4change.nut4healthcentrotratamiento.data.toDomainComplication
import org.sic4change.nut4healthcentrotratamiento.data.toDomainContract
import org.sic4change.nut4healthcentrotratamiento.data.toDomainMalNutritionChildTable
import org.sic4change.nut4healthcentrotratamiento.data.toDomainMalNutritionTeenagerTable
import org.sic4change.nut4healthcentrotratamiento.data.toDomainPoint
import org.sic4change.nut4healthcentrotratamiento.data.toDomainSymtom
import org.sic4change.nut4healthcentrotratamiento.data.toDomainTreatment
import org.sic4change.nut4healthcentrotratamiento.data.toDomainTutor
import org.sic4change.nut4healthcentrotratamiento.data.toDomainUser
import org.sic4change.nut4healthcentrotratamiento.data.toDomainVisit
import org.sic4change.nut4healthcentrotratamiento.data.toServerCase
import org.sic4change.nut4healthcentrotratamiento.data.toServerChild
import org.sic4change.nut4healthcentrotratamiento.data.toServerTutor
import org.sic4change.nut4healthcentrotratamiento.data.toServerVisit
import timber.log.Timber
import java.util.Date

import com.google.firebase.firestore.ktx.memoryCacheSettings
import com.google.firebase.firestore.ktx.persistentCacheSettings
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.firestore.FirebaseFirestoreSettings

import kotlinx.coroutines.withTimeout
import com.google.firebase.firestore.Source
import org.sic4change.nut4healthcentrotratamiento.data.entitities.STATUS

object FirebaseDataSource {

    private val firestoreAuth = NUT4HealthFirebaseService.fbAuth
    private val firestore = NUT4HealthFirebaseService.mFirestore
    private val remoteConfig = NUT4HealthFirebaseService.remoteConfig
    private var source = Source.DEFAULT

    fun getFirestoreSource(): Source {
        return source
    }

    fun setFirestoreSource(conexion: Boolean) {
        if (conexion) {
            source = Source.SERVER
        } else {
            source = Source.CACHE
        }
    }

    fun configFirestore() {
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        firestore.firestoreSettings = settings
    }

    /*suspend fun checkFirebaseConnection() : Boolean = withContext(Dispatchers.IO) {
        try {
            val conexionRef = firestore.collection("conexion")
            val query = conexionRef.whereEqualTo("conexion", true).limit(1)
            val result = query.get(Source.SERVER).await()
            return@withContext result.size() > 0
        } catch (e: Exception) {
            false
        }
    }*/

    /*suspend fun loadInitialData(): Boolean  = withContext(Dispatchers.IO) {
        firestore.collection("configurations").get().await()
        firestore.collection("cities").get().await()
        firestore.collection("countries").get().await()
        firestore.collection("provinces").get().await()
        firestore.collection("regions").get().await()
        firestore.collection("users").get().await()
        firestore.collection("points").get().await()
        firestore.collection("tutors").get().await()
        firestore.collection("childs").get().await()
        firestore.collection("cases").get().await()
        firestore.collection("contracts").get().await()
        firestore.collection("visits").get().await()
        firestore.collection("complications").get().await()
        firestore.collection("symtoms").get().await()
        firestore.collection("treatments").get().await()
        firestore.collection("malnutritionTeenagersTable").get().await()
        firestore.collection("malnutritionChildTable").get().await()
        firestore.collection("malnutritionAdultTable").get().await()
        Timber.d("Getting inntial data result: ok")
        true
    }*/

    suspend fun checkUpdateGooglePlayVersion(versionCode: String) : Boolean = withContext(Dispatchers.IO) {
        try {
            val configSettings = remoteConfigSettings {
                //minimumFetchIntervalInSeconds = 3600
                minimumFetchIntervalInSeconds = 60
            }
            remoteConfig.setConfigSettingsAsync(configSettings)
            remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                }
            }.await()
            remoteConfig.getString("android_latest_version_name").toDouble() > versionCode.toDouble()
        } catch (e: Exception) {
            false
        }

    }

    suspend fun isLogged(): Boolean = withContext(Dispatchers.IO) {
        firestoreAuth.currentUser != null
    }

    suspend fun getLoggedUser(): org.sic4change.nut4healthcentrotratamiento.data.entitities.User =
        withContext(Dispatchers.IO) {
            val userRef = firestore.collection("users")
            val query = userRef.whereEqualTo("email", firestoreAuth.currentUser!!.email).limit(1)
            val result = query.get(source).await()
            val networkUserContainer = NetworkUsersContainer(result.toObjects(User::class.java))
            networkUserContainer.results[0].let {
                it.toDomainUser()
            }
        }

    suspend fun getUser(email: String): org.sic4change.nut4healthcentrotratamiento.data.entitities.User? = withContext(Dispatchers.IO) {
        val userRef = firestore.collection("users")
        val query = userRef.whereEqualTo("email", email).limit(1)
        val result = query.get(source).await()
        val networkUserContainer = NetworkUsersContainer(result.toObjects(User::class.java))
        try {
            networkUserContainer.results[0].let {
                it.toDomainUser()
            }
        } catch (e : Exception) {
            null
        }
    }

    suspend fun login(email: String, password: String): Boolean {
        var result = false
        withContext(Dispatchers.IO) {
            try {
                val login = firestoreAuth.signInWithEmailAndPassword(email, password).await()
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
            firestoreAuth.signOut()
        }
    }

    suspend fun forgotPassword(email: String): Boolean {
        var result = false
        withContext(Dispatchers.IO) {
            Timber.d("try to request change password with firebase")
            try {
                firestoreAuth.sendPasswordResetEmail(email).await()
                Timber.d("Request change password: ok")
                result = true
            } catch (ex : Exception) {
                Timber.d("Request change password: error ${ex.message}")
            }
        }
        return result
    }

    suspend fun updatePhotoAvatar(fileUri: String) : String {
        var result = ""
        withContext(Dispatchers.IO) {
            val userRef = firestore.collection("users")
            val queryUser = userRef.whereEqualTo("email", firestoreAuth.currentUser!!.email).limit(1)
            val resultUser = queryUser.get(source).await()
            val networkUserContainer = NetworkUsersContainer(resultUser.toObjects(User::class.java))
            networkUserContainer.results[0].let { user ->
                Timber.d("try to request change avatar image with firebase")
                val refStorage = FirebaseStorage.getInstance().reference.child("avatars/${user.id}/avatar_medical_${System.currentTimeMillis()}")
                try {
                    val resultUpload = refStorage.putFile(Uri.parse(fileUri)).await()
                    resultUpload.let {
                        val url = it.storage.downloadUrl.await()
                        url.let {
                            result = it.toString()
                            user.photo = result
                            val usersRef = firestore.collection("users")
                            usersRef.document(user.id).set(user).await()
                            Timber.d("update photo user result: ok")
                        }
                    }
                } catch (ex : Exception) {
                    Timber.d("error when upload image to firebase storage: ${ex.message}")
                }

            }
        }
        return result
    }

    suspend fun getPoint(id: String?): org.sic4change.nut4healthcentrotratamiento.data.entitities.Point? = withContext(Dispatchers.IO) {
        val pointsRef = firestore.collection("points")
        val query = pointsRef.whereEqualTo("id", id)
        val result = query.get(source).await()
        val networkPointsContainer = NetworkPointsContainer(result.toObjects(Point::class.java))
        try {
            networkPointsContainer.results[0].let {
                it.toDomainPoint()
            }
        } catch (e : Exception) {
            null
        }
    }

    suspend fun getTutors(): List<org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor> = withContext(Dispatchers.IO) {
        val userRef = firestore.collection("users")
        val queryUser = userRef.whereEqualTo("email", firestoreAuth.currentUser!!.email).limit(1)
        val resultUser = queryUser.get(source).await()
        val networkUserContainer = NetworkUsersContainer(resultUser.toObjects(User::class.java))
        networkUserContainer.results[0].let {
            val point = it.point
            val tutorsRef = firestore.collection("tutors")
            val query = tutorsRef.whereEqualTo("point", point).orderBy("name", Query.Direction.ASCENDING )
            val result = query.get(source).await()
            val networkTutorsContainer = NetworkTutorsContainer(result.toObjects(Tutor::class.java))
            networkTutorsContainer.results.filter { it.active }.map { it.toDomainTutor() }
        }
    }

    suspend fun getTutor(id: String): org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor? = withContext(Dispatchers.IO) {
        val tutorsRef = firestore.collection("tutors")
        val query = tutorsRef.whereEqualTo("id", id)
        val result = query.get(source).await()
        val networkTutorsContainer = NetworkTutorsContainer(result.toObjects(Tutor::class.java))
        try {
            networkTutorsContainer.results[0].let {
                it.toDomainTutor()
            }
        } catch (e : Exception) {
            null
        }
    }

    suspend fun createTutor(tutor: org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor) : String = withContext(Dispatchers.IO) {
        Timber.d("try to create tutor with firebase")
        try {
            val userRef = firestore.collection("users")
            val query = userRef.whereEqualTo("email", firestoreAuth.currentUser!!.email).limit(1)
            val result = query.get(source).await()
            val networkUserContainer = NetworkUsersContainer(result.toObjects(User::class.java))
            networkUserContainer.results[0].let {
                val user = it.toDomainUser()
                tutor.point = user.point
                val tutorsRef = firestore.collection("tutors")
                val id = "${user.id}_${tutor.createDate.time}"
                tutor.id = id
                tutorsRef.document(tutor.id).set(tutor.toServerTutor())
                Timber.d("Create tutor result: ok")
                id
            }
        } catch (ex : Exception) {
            Timber.d("Create tutor result: false ${ex.message}")
            "null"
        }
    }

    suspend fun deleteTutor(id: String) {
        withContext(Dispatchers.IO) {
            Timber.d("try to delete tutor from firebase")
            try {
                val tutorRef = firestore.collection("tutors")
                tutorRef.document(id).delete().await()
                Timber.d("Delete tutor result: ok")
            } catch (ex: Exception) {
                Timber.d("Delete tutor result: false ${ex.message}")
            }
        }
    }

    suspend fun updateTutor(tutor: org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor) {
        withContext(Dispatchers.IO) {
            Timber.d("try to update tutor from firebase")
            try {
                val userRef = firestore.collection("users")
                val queryUser = userRef.whereEqualTo("email", firestoreAuth.currentUser!!.email).limit(1)
                val resultUser = queryUser.get(source).await()
                val networkUserContainer = NetworkUsersContainer(resultUser.toObjects(User::class.java))
                networkUserContainer.results[0].let { user ->
                    tutor.point = user.point
                    val tutorsRef = firestore.collection("tutors")
                    val tutorDoc = tutorsRef.document(tutor.id).get(source).await()
                    tutorsRef.document(tutor.id).set(tutor.toServerTutor()).await()
                    tutorsRef.document(tutor.id).update("createDate", tutorDoc.get("createDate")).await()
                    Timber.d("update tutor result: ok")
                }

            } catch (ex: Exception) {
                Timber.d("update tutor result: false ${ex.message}")
            }
        }
    }

    suspend fun getChilds(tutorId: String): List<org.sic4change.nut4healthcentrotratamiento.data.entitities.Child> = withContext(Dispatchers.IO) {
        val childsRef = firestore.collection("childs")
        val query = childsRef.whereEqualTo("tutorId", tutorId).orderBy("name", Query.Direction.ASCENDING )
        val result = query.get(source).await()
        val networkChildsContainer = NetworkChildsContainer(result.toObjects(Child::class.java))
        networkChildsContainer.results.map { it.toDomainChild() }
    }

    suspend fun getChild(id: String): org.sic4change.nut4healthcentrotratamiento.data.entitities.Child? = withContext(Dispatchers.IO) {
        val childRef = firestore.collection("childs")
        val query = childRef.whereEqualTo("id", id)
        val result = query.get(source).await()
        val networkChildsContainer = NetworkChildsContainer(result.toObjects(Child::class.java))
        if (networkChildsContainer.results.isNotEmpty()) {
            networkChildsContainer.results[0].let {
                it.toDomainChild()
            }
        } else {
            null
        }
    }

    suspend fun createChild(child: org.sic4change.nut4healthcentrotratamiento.data.entitities.Child) {
        withContext(Dispatchers.IO) {
            Timber.d("try to create child with firebase")
            try {
                val userRef = firestore.collection("tutors")
                val query = userRef.whereEqualTo("id", child.tutorId)
                val result = query.get(source).await()
                val networkTutorContainer = NetworkTutorsContainer(result.toObjects(Tutor::class.java))
                networkTutorContainer.results[0].let {
                    val tutor = it.toDomainTutor()
                    child.point = tutor.point
                    child.code = "${tutor.phone}-${child.brothers}"
                    val childsRef = firestore.collection("childs")
                    val id = "${tutor.id}_${child.createDate.time}"
                    child.id = id
                    childsRef.document(child.id).set(child.toServerChild())
                    Timber.d("Create child result: ok")
                }
            } catch (ex : Exception) {
                Timber.d("Create child result: false ${ex.message}")
            }
        }
    }

    suspend fun deleteChild(id: String) {
        withContext(Dispatchers.IO) {
            Timber.d("try to delete child from firebase")
            try {
                val childRef = firestore.collection("childs")
                childRef.document(id).delete().await()
                Timber.d("Delete child result: ok")
            } catch (ex: Exception) {
                Timber.d("Delete tutor child: false ${ex.message}")
            }
        }
    }

    suspend fun updateChild(child: org.sic4change.nut4healthcentrotratamiento.data.entitities.Child) {
        withContext(Dispatchers.IO) {
            Timber.d("try to update child from firebase")
            try {
                val userRef = firestore.collection("tutors")
                val query = userRef.whereEqualTo("id", child.tutorId)
                val result = query.get(source).await()
                val networkTutorContainer = NetworkTutorsContainer(result.toObjects(Tutor::class.java))
                networkTutorContainer.results[0].let {
                    child.point = it.point
                    child.code = "${it.phone}-${child.brothers}"
                    val childsRef = firestore.collection("childs")
                    childsRef.document(child.id).set(child.toServerChild()).await()
                    Timber.d("update child result: ok")
                }
            } catch (ex: Exception) {
                Timber.d("update child result: false ${ex.message}")
            }
        }
    }

    suspend fun getChildCases(childId: String): List<org.sic4change.nut4healthcentrotratamiento.data.entitities.Case> = withContext(Dispatchers.IO) {
        val casesRef = firestore.collection("cases")
        val query = casesRef.whereEqualTo("childId", childId).orderBy("lastdate", Query.Direction.DESCENDING )
        val result = query.get(source).await()
        val networkCasesContainer = NetworkCasesContainer(result.toObjects(Case::class.java))
        networkCasesContainer.results.map { it.toDomainCase() }
    }

    suspend fun getFEFACases(fefaId: String): List<org.sic4change.nut4healthcentrotratamiento.data.entitities.Case> = withContext(Dispatchers.IO) {
        val casesRef = firestore.collection("cases")
        val query = casesRef.whereEqualTo("fefaId", fefaId).orderBy("lastdate", Query.Direction.DESCENDING )
        val result = query.get(source).await()
        val networkCasesContainer = NetworkCasesContainer(result.toObjects(Case::class.java))
        networkCasesContainer.results.map { it.toDomainCase() }
    }

    suspend fun createCase(case: org.sic4change.nut4healthcentrotratamiento.data.entitities.Case) : org.sic4change.nut4healthcentrotratamiento.data.entitities.Case?
            = withContext(Dispatchers.IO) {
        Timber.d("try to create case with firebase")
        try {
            val userRef = firestore.collection("users")
            val queryUser =
                userRef.whereEqualTo("email", firestoreAuth.currentUser!!.email).limit(1)
            val resultUser = queryUser.get(source).await()
            val networkUserContainer = NetworkUsersContainer(resultUser.toObjects(User::class.java))
            networkUserContainer.results[0].let { user ->
                if (case.fefaId == null) {
                    val childsRef = firestore.collection("childs")
                    val queryChild = childsRef.whereEqualTo("id", case.childId)
                    val resultChild = queryChild.get(source).await()
                    val networkChildsContainer = NetworkChildsContainer(resultChild.toObjects(Child::class.java))
                    networkChildsContainer.results[0].let {
                        val tutorId = it.toDomainChild().tutorId
                        val pointId = user.point
                        val caseToUpload =
                            org.sic4change.nut4healthcentrotratamiento.data.entitities.Case(
                                case.id, case.childId, case.fefaId, tutorId, case.name, case.status, case.createdate,
                                case.lastdate, case.visits, case.observations, pointId
                            )
                        val casesRef = firestore.collection("cases")

                        val id = "${case.childId}_${case.createdate.time}"
                        caseToUpload.id = id
                        casesRef.document(caseToUpload.id).set(caseToUpload.toServerCase())
                        Timber.d("Create case result: ok")
                        caseToUpload
                    }
                } else {
                    val tutorId = case.fefaId
                    val pointId = user.point
                    val caseToUpload =
                        org.sic4change.nut4healthcentrotratamiento.data.entitities.Case(
                            case.id, "", case.fefaId, tutorId, case.name, case.status, case.createdate,
                            case.lastdate, case.visits, case.observations, pointId
                        )
                    val casesRef = firestore.collection("cases")
                    val id = "${case.tutorId}_${case.createdate.time}"
                    caseToUpload.id = id
                    casesRef.document(caseToUpload.id).set(caseToUpload.toServerCase())
                    Timber.d("Create case result: ok")
                    caseToUpload
                }


            }

        } catch (ex: Exception) {
            Timber.d("Create case result: false ${ex.message}")
            null
        }
    }

    suspend fun getCase(id: String): org.sic4change.nut4healthcentrotratamiento.data.entitities.Case? = withContext(Dispatchers.IO) {
        val caseRef = firestore.collection("cases")
        val query = caseRef.whereEqualTo("id", id)
        val result = query.get(source).await()
        val networkCasesContainer = NetworkCasesContainer(result.toObjects(Case::class.java))
        try {
            networkCasesContainer.results[0].let {
                it.toDomainCase()
            }
        } catch (e : Exception) {
            null
        }
    }

    suspend fun deleteCase(id: String) {
        withContext(Dispatchers.IO) {
            Timber.d("try to delete case from firebase")
            try {
                val caseRef = firestore.collection("cases")
                caseRef.document(id).delete().await()
                Timber.d("Delete case result: ok")
            } catch (ex: Exception) {
                Timber.d("Delete case: false ${ex.message}")
            }
        }
    }

    suspend fun updateCase(case: org.sic4change.nut4healthcentrotratamiento.data.entitities.Case) {
        withContext(Dispatchers.IO) {
            Timber.d("try to update case from firebase")
            try {
                val userRef = firestore.collection("users")
                val queryUser = userRef.whereEqualTo("email", firestoreAuth.currentUser!!.email).limit(1)
                val resultUser = queryUser.get(source).await()
                val networkUserContainer = NetworkUsersContainer(resultUser.toObjects(User::class.java))
                networkUserContainer.results[0].let { user ->
                    case.point = user.point
                    val casesRef = firestore.collection("cases")
                    casesRef.document(case.id).set(case.toServerCase()).await()
                    Timber.d("update case result: ok")
                }

            } catch (ex: Exception) {
                Timber.d("update case result: false ${ex.message}")
            }
        }
    }

    suspend fun checkDiagnosis(phone: String) {
        withContext(Dispatchers.IO) {
            val contractsRef = firestore.collection("contracts")
            val query = contractsRef.whereEqualTo("childPhoneContract", phone.filter { !it.isWhitespace() })
            val result = query.get(source).await()
            val networkContractContainer = NetworkContractContainer(result.toObjects(Contract::class.java))
            if (networkContractContainer.results.isNotEmpty()) {
                networkContractContainer.results[0].let {
                    val contract = it.toDomainContract()
                    val contractRef = firestore.collection("contracts")
                    contractRef.document(contract.id)
                        .update(
                            "status", "FINISH",
                            "medicalDate", System.currentTimeMillis().toString(),
                            "medicalDateMiliseconds", System.currentTimeMillis(),
                            "medicalDateToUpdate", System.currentTimeMillis().toString(),
                            "medicalDateToUpdateInMilis", System.currentTimeMillis(),
                        ).await()
                }
            }
        }

    }
    suspend fun checkTutorByPhone(phone: String): org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor? = withContext(Dispatchers.IO) {
        val userRef = firestore.collection("users")
        val queryUser = userRef.whereEqualTo("email", firestoreAuth.currentUser!!.email).limit(1)
        val resultUser = queryUser.get(source).await()
        val networkUserContainer = NetworkUsersContainer(resultUser.toObjects(User::class.java))
        networkUserContainer.results[0].let { user ->
            val tutorsRef = firestore.collection("tutors")
            val query = tutorsRef.whereEqualTo("phone", phone.filter { !it.isWhitespace() })
            val result = query.get(source).await()
            val networkTutorsContainer = NetworkTutorsContainer(result.toObjects(Tutor::class.java))
            try {
                networkTutorsContainer.results[0].let {
                    if (it.point == user.point) {
                        it.toDomainTutor()
                    } else {
                        null
                    }
                }
            } catch (e : Exception) {
                null
            }
        }

    }

    suspend fun getMalnutritionChildTable(): List<org.sic4change.nut4healthcentrotratamiento.data.entitities.MalNutritionChildTable> = withContext(Dispatchers.IO) {
        val malnutritionChildTableRef = firestore.collection("malnutritionChildTable")
        val query = malnutritionChildTableRef.whereEqualTo("createdby", "aasencio@sic4change.org")
        val result = query.get(source).await()
        val networkMalnutritionChildTableContainer = NetworkMalNutritionChildTableContainer(result.toObjects(MalNutritionChildTable::class.java))
        networkMalnutritionChildTableContainer.results.map { it.toDomainMalNutritionChildTable() }
    }

    suspend fun getMalnutritionTeenegerTable(): List<org.sic4change.nut4healthcentrotratamiento.data.entitities.MalNutritionTeenagerTable> = withContext(Dispatchers.IO) {
        val casesRef = firestore.collection("malnutritionTeenagersTable")
        val query = casesRef.whereEqualTo("createdby", "aasencio@sic4change.org")
        val result = query.get(source).await()
        val networkMalNutritionTeenagerTableContainer = NetworkMalNutritionTeenagerTableContainer(result.toObjects(MalNutritionTeenagerTable::class.java))
        networkMalNutritionTeenagerTableContainer.results.map { it.toDomainMalNutritionTeenagerTable() }
    }

    suspend fun getSymtoms(): List<org.sic4change.nut4healthcentrotratamiento.data.entitities.Symtom> = withContext(Dispatchers.IO) {
        val casesRef = firestore.collection("symtoms")
        val query = casesRef.whereNotEqualTo("name", "")
        val result = query.get(source).await()
        val networkSymtomContainer = NetworkSymtomContainer(result.toObjects(Symtom::class.java))
        networkSymtomContainer.results.map { it.toDomainSymtom() }
    }

    suspend fun getTreatments(): List<org.sic4change.nut4healthcentrotratamiento.data.entitities.Treatment> = withContext(Dispatchers.IO) {
        val casesRef = firestore.collection("treatments")
        val query = casesRef.whereEqualTo("active", true)
        val result = query.get(source).await()
        val networkTreatmentContainer = NetworkTreatmentContainer(result.toObjects(Treatment::class.java))
        networkTreatmentContainer.results.map { it.toDomainTreatment() }
    }

    suspend fun getComplications(): List<org.sic4change.nut4healthcentrotratamiento.data.entitities.Complication> = withContext(Dispatchers.IO) {
        val casesRef = firestore.collection("complications")
        val query = casesRef.whereNotEqualTo("name", "")
        val result = query.get(source).await()
        val networkComplicationContainer = NetworkComplicationContainer(result.toObjects(Complication::class.java))
        networkComplicationContainer.results.map { it.toDomainComplication() }
    }

    suspend fun getVisits(caseId: String): List<org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit> = withContext(Dispatchers.IO) {
        val visitsRef = firestore.collection("visits")
        val query = visitsRef.whereEqualTo("caseId", caseId).orderBy("createdate", Query.Direction.DESCENDING )
        val result = query.get(source).await()
        val networkVisitsContainer = NetworkVisitContainer(result.toObjects(Visit::class.java))
        networkVisitsContainer.results.map { it.toDomainVisit() }
    }

    suspend fun getVisit(id: String): org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit? = withContext(Dispatchers.IO) {
        val visitRef = firestore.collection("visits")
        val query = visitRef.whereEqualTo("id", id)
        val result = query.get(source).await()
        val networkVisitContainer = NetworkVisitContainer(result.toObjects(Visit::class.java))
        try {
            networkVisitContainer.results[0].let {
                it.toDomainVisit()
            }
        } catch (e : Exception) {
            null
        }
    }

    suspend fun deleteVisit(id: String) {
        withContext(Dispatchers.IO) {
            Timber.d("try to delete visit from firebase")
            try {
                val visitRef = firestore.collection("visits")
                visitRef.document(id).delete().await()
                Timber.d("Delete visit result: ok")
            } catch (ex: Exception) {
                Timber.d("Delete visit: false ${ex.message}")
            }
        }
    }

    suspend fun createVisit(visit: org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit) {
        withContext(Dispatchers.IO) {
            Timber.d("try to create visit with firebase")
            try {
                val userRef = firestore.collection("users")
                val queryUser = userRef.whereEqualTo("email", firestoreAuth.currentUser!!.email).limit(1)
                val resultUser = queryUser.get(source).await()
                val networkUserContainer = NetworkUsersContainer(resultUser.toObjects(User::class.java))
                networkUserContainer.results[0].let { user ->
                    val casesRef = firestore.collection("cases")
                    val queryCase = casesRef.whereEqualTo("id", visit.caseId)
                    val resultCase = queryCase.get(source).await()
                    val networkCasesContainer = NetworkCasesContainer(resultCase.toObjects(Case::class.java))
                    networkCasesContainer.results[0].let { case ->
                        val visitToUpdate = org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit(
                            "", case.id, case.childId, case.fefaId, case.tutorId, visit.createdate,
                            visit.admissionType, visit.height, visit.weight, visit.imc, visit.armCircunference,
                            visit.status, visit.edema, visit.respiratonStatus, visit.appetiteTest, visit.infection,
                            visit.eyesDeficiency, visit.deshidratation, visit.vomiting, visit.diarrhea,
                            visit.fever, visit.cough, visit.temperature, visit.vitamineAVaccinated,
                            visit.acidfolicAndFerroVaccinated, visit.vaccinationCard, visit.rubeolaVaccinated,
                            visit.amoxicilina, visit.otherTratments,
                            visit.complications.filter { it.selected }.toMutableList(),
                            visit.observations, user.point)
                        val visitsRef = firestore.collection("visits")
                        val id = "${case.childId}_${visit.createdate.time}"
                        visitToUpdate.id = id
                        visitsRef.document(visitToUpdate.id).set(visitToUpdate.toServerVisit())
                        Timber.d("Create visit result: ok")
                        val caseRef = firestore.collection("cases")
                        val query = caseRef.whereEqualTo("id", visit.caseId)
                        val result = query.get(source).await()
                        val networkCaseContainer = NetworkCasesContainer(result.toObjects(Case::class.java))
                        networkCaseContainer.results[0].let { case ->
                            val visits = case.visits
                            var status = case.status
                            if (visits == 0 && (visit.status == "Poids Normal" || visit.status == "Poids Cible")) {
                                status = "Fermé"
                            } else if (visits == 0 && (visit.status == "Normopeso" || visit.status == "Peso objetivo")) {
                                status = "Cerrado"
                            }
                            caseRef.document(visit.caseId)
                                .update(
                                    "visits", visits + 1, "status", status,
                                    "lastdate", Date()
                                )
                        }

                    }
                }

            } catch (ex : Exception) {
                Timber.d("Create visit result: false ${ex.message}")
            }
        }
    }

    suspend fun updateVisit(visit: org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit) {
        withContext(Dispatchers.IO) {
            Timber.d("try to update visit from firebase")
            try {
                val userRef = firestore.collection("users")
                val queryUser = userRef.whereEqualTo("email", firestoreAuth.currentUser!!.email).limit(1)
                val resultUser = queryUser.get(source).await()
                val networkUserContainer = NetworkUsersContainer(resultUser.toObjects(User::class.java))
                networkUserContainer.results[0].let { user ->
                    visit.point = user.point
                    val visitsRef = firestore.collection("visits")
                    visitsRef.document(visit.id).set(visit.toServerVisit()).await()
                    Timber.d("update visit result: ok")
                }

            } catch (ex: Exception) {
                Timber.d("update visit result: false ${ex.message}")
            }
        }
    }

    suspend fun checkDesnutrition(height: Double, weight: Double): Double = withContext(Dispatchers.IO) {
        var status = 0.0
        if (height > 120 && height <= 155) {
            val childMalNutritionTeenagerTableRef = firestore.collection("malnutritionTeenagersTable")
            val queryChildMalNutritionTeenagerTable = childMalNutritionTeenagerTableRef.whereEqualTo("sex", "mf").whereGreaterThanOrEqualTo("cm", (height - 0.1).toString())
                .orderBy("cm", Query.Direction.ASCENDING).limit(1)
            val resultChildMalNutritionTeenagerTable = queryChildMalNutritionTeenagerTable.get(source).await()
            val networkMalNutritionTeenagerTableContainer =
                NetworkMalNutritionTeenagerTableContainer(resultChildMalNutritionTeenagerTable.toObjects(MalNutritionTeenagerTable::class.java))
            networkMalNutritionTeenagerTableContainer.results[0].let { malNutritionTeenagerTable ->
                if (weight >= malNutritionTeenagerTable.hundred.toDouble()) {
                    status = 100.0
                } else if (weight >= malNutritionTeenagerTable.eightyfive.toDouble()) {
                    status = 100.0
                } else if (weight >= malNutritionTeenagerTable.eighty.toDouble()) {
                    status = 85.0
                } else if (weight >= malNutritionTeenagerTable.seventy.toDouble()) {
                    status = 80.0
                } else  {
                    status = 70.0
                }
            }
        } else if (height >= 45) {
            val childMalNutritionChildTableRef = firestore.collection("malnutritionChildTable")
            val queryChildMalNutritionChildTable = childMalNutritionChildTableRef
                .whereGreaterThanOrEqualTo("cm", (height - 0.1).toString()).orderBy("cm", Query.Direction.ASCENDING).limit(1)
            val resultChildMalNutritionChildTable = queryChildMalNutritionChildTable.get(source).await()
            val networkMalNutritionChildTableContainer =
                NetworkMalNutritionChildTableContainer(resultChildMalNutritionChildTable.toObjects(MalNutritionChildTable::class.java))
            try {
                networkMalNutritionChildTableContainer.results[0].let { malNutritionChldTable ->
                    if (weight >= malNutritionChldTable.minusone.toDouble()) {
                        status = 0.0
                    } else if (weight >= malNutritionChldTable.minustwo.toDouble()) {
                        status = -1.0
                    } else if (weight >= malNutritionChldTable.minusthree.toDouble()) {
                        status = -1.5
                    }  else  {
                        status = -3.0
                    }
                }
            } catch (e : Exception) {
                if (height.toString() == "100.0") {
                    val malNutritionChldTable = MalNutritionChildTable(
                        "X3fX5g2Fd9lpy0OVYkgA",
                        "100",
                        "14.2",
                        "13.6",
                        "12.1",
                        "13.1",
                        "15.4"
                    )
                    if (weight >= malNutritionChldTable.minusone.toDouble()) {
                        status = 0.0
                    } else if (weight >= malNutritionChldTable.minustwo.toDouble()) {
                        status = -1.0
                    } else if (weight >= malNutritionChldTable.minusthree.toDouble()) {
                        status = -1.5
                    }  else  {
                        status = -3.0
                    }
                }
            }

        }
        status
    }

    suspend fun checkAdultDesnutrition(height: Double, weight: Double): Double = withContext(Dispatchers.IO) {
        var status = 0.0
        val childMalNutritionAdultTableRef = firestore.collection("malnutritionAdultTable")
        val queryChildMalNutritionAdultTable = childMalNutritionAdultTableRef.whereGreaterThanOrEqualTo("cm", height - 0.1)
            .orderBy("cm", Query.Direction.ASCENDING).limit(1)
        val resultChildMalNutritionAdultTable = queryChildMalNutritionAdultTable.get(source).await()
        val networkMalNutritionAdultTableContainer =
            NetworkMalNutritionAdultTableContainer(resultChildMalNutritionAdultTable.toObjects(MalNutritionAdultTable::class.java))
        networkMalNutritionAdultTableContainer.results[0].let { malNutritionAdultTable ->
            if (weight >= malNutritionAdultTable.eighteenfive) {
                status = 18.5
            } else if (weight >= malNutritionAdultTable.eighteen) {
                status = 18.0
            } else if (weight >= malNutritionAdultTable.seventeen) {
                status = 17.0
            } else if (weight >= malNutritionAdultTable.sixteen) {
                status = 16.0
            } else  {
                status = 15.0
            }
        }
        status
    }

    suspend fun getActiveCases(): List<Cuadrant?> = withContext(Dispatchers.IO) {
        val userRef = firestore.collection("users")
        val queryUser = userRef.whereEqualTo("email", firestoreAuth.currentUser!!.email).limit(1)
        val resultUser = queryUser.get(source).await()
        val networkUserContainer = NetworkUsersContainer(resultUser.toObjects(User::class.java))
        networkUserContainer.results[0].let { user ->
            val tutorsRef = firestore.collection("tutors")
            val queryTutor = tutorsRef.whereEqualTo("point", user.point).orderBy("name", Query.Direction.ASCENDING)
            val resultTutor = queryTutor.get(source).await()
            val networkTutorsContainer =
                NetworkTutorsContainer(resultTutor.toObjects(Tutor::class.java))
            val tutors = networkTutorsContainer.results

            val pointsRef = firestore.collection("points")
            val queryPoint = pointsRef.whereEqualTo("pointId", user.point).orderBy("name", Query.Direction.ASCENDING)
            val resultPoint= queryPoint.get(source).await()
            val networkPointsContainer =
                NetworkPointsContainer(resultPoint.toObjects(Point::class.java))
            val point = networkPointsContainer.results[0]

            val childsRef = firestore.collection("childs")
            val queryChilds = childsRef.whereEqualTo("point", user.point).orderBy("name", Query.Direction.ASCENDING)
            val resultChild = queryChilds.get(source).await()
            val networkChildsContainer =
                NetworkChildsContainer(resultChild.toObjects(Child::class.java))
            val childs = networkChildsContainer.results

            val visitsRef = firestore.collection("visits")
            val queryVisit =
                visitsRef.whereEqualTo("point", user.point).orderBy("createdate", Query.Direction.DESCENDING)
            val resultVisit = queryVisit.get(source).await()
            val networkVisitsContainer =
                NetworkVisitContainer(resultVisit.toObjects(Visit::class.java))
            val visits = networkVisitsContainer.results.map { it.toDomainVisit() }

            val casesRef = firestore.collection("cases")
            val query = casesRef.whereIn("status", STATUS.OPEN_STATUS_VALUES)
                .orderBy("lastdate", Query.Direction.DESCENDING)
            val result = query.get(source).await()
            val networkCasesContainer = NetworkCasesContainer(result.toObjects(Case::class.java))
            networkCasesContainer.results.filter { it.point == user.point }.map { case ->
                val child = childs.findLast { it.id == case.childId }
                val tutor = tutors.findLast { it.id == case.tutorId }
                var visitsToAdd : MutableList<org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit> = arrayListOf()
                visits.forEach {
                    if (it.caseId == case.id) {
                        visitsToAdd.add(it)
                    }
                }
                if (tutor != null && case.childId != null) {
                    Cuadrant(
                        case.id,
                        case.childId,
                        (child?.name ?: "") + " " + (child?.surnames ?: ""),
                        case.tutorId,
                        tutor?.name ?: "",
                        tutor?.surnames ?: "",
                        case.name,
                        case.status,
                        case.createdate,
                        case.lastdate,
                        visitsToAdd.toList(),
                        case.visits.toString(),
                        point.type,
                        case.observations
                    )
                } else {
                    null
                }
            }
        }

    }

    suspend fun subscribeToPointNotifications() = withContext(Dispatchers.IO) {
        try {
            val userRef = firestore.collection("users")
            val queryUser = userRef.whereEqualTo("email", firestoreAuth.currentUser!!.email).limit(1)
            val resultUser = queryUser.get(source).await()
            val networkUserContainer = NetworkUsersContainer(resultUser.toObjects(User::class.java))
            networkUserContainer.results[0].let {
                val point = it.point
                FirebaseMessaging.getInstance().subscribeToTopic(point)
            }
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
        }

    }

    suspend fun unsubscribeToPointNotifications(point: String) = withContext(Dispatchers.IO) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(point)
    }

}


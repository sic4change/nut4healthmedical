package org.sic4change.nut4healthcentrotratamiento.data.network

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.sic4change.nut4healthcentrotratamiento.data.*
import timber.log.Timber
import java.util.*


object FirebaseDataSource {

    private val firestoreAuth = NUT4HealthFirebaseService.fbAuth
    private val firestore = NUT4HealthFirebaseService.mFirestore

    suspend fun isLogged(): Boolean = withContext(Dispatchers.IO) {

        firestoreAuth.currentUser != null
    }

    suspend fun getLoggedUser(): org.sic4change.nut4healthcentrotratamiento.data.entitities.User =
        withContext(Dispatchers.IO) {
            val firestoreAuth = NUT4HealthFirebaseService.fbAuth
            val userRef = firestore.collection("users")
            val query = userRef.whereEqualTo("email", firestoreAuth.currentUser!!.email).limit(1)
            val result = query.get().await()
            val networkUserContainer = NetworkUsersContainer(result.toObjects(User::class.java))
            networkUserContainer.results[0].let {
                it.toDomainUser()
            }
        }

    suspend fun getUser(email: String): org.sic4change.nut4healthcentrotratamiento.data.entitities.User = withContext(Dispatchers.IO) {
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
        val tutorsRef = firestore.collection("tutors")
        val query = tutorsRef.whereEqualTo("active", true).orderBy("name", Query.Direction.ASCENDING )
        val result = query.get().await()
        val networkTutorsContainer = NetworkTutorsContainer(result.toObjects(Tutor::class.java))
        networkTutorsContainer.results.map { it.toDomainTutor() }
    }

    suspend fun getTutor(id: String): org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor = withContext(Dispatchers.IO) {
        val tutorsRef = firestore.collection("tutors")
        val query = tutorsRef.whereEqualTo("id", id)
        val result = query.get().await()
        val networkTutorsContainer = NetworkTutorsContainer(result.toObjects(Tutor::class.java))
        networkTutorsContainer.results[0].let {
            it.toDomainTutor()
        }
    }

    suspend fun createTutor(tutor: org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor) {
        withContext(Dispatchers.IO) {
            Timber.d("try to create tutor with firebase")
            try {
                val tutorsRef = firestore.collection("tutors")
                val id = tutorsRef.add(tutor.toServerTutor()).await().id
                tutorsRef.document(id).update("id", id,).await()
                Timber.d("Create tutor result: ok")
            } catch (ex : Exception) {
                Timber.d("Create tutor result: false ${ex.message}")
            }
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
                val tutorsRef = firestore.collection("tutors")
                tutorsRef.document(tutor.id).set(tutor.toServerTutor()).await()
                Timber.d("update tutor result: ok")
            } catch (ex: Exception) {
                Timber.d("update tutor result: false ${ex.message}")
            }
        }
    }

    suspend fun getChilds(tutorId: String): List<org.sic4change.nut4healthcentrotratamiento.data.entitities.Child> = withContext(Dispatchers.IO) {
        val childsRef = firestore.collection("childs")
        val query = childsRef.whereEqualTo("tutorId", tutorId).orderBy("name", Query.Direction.ASCENDING )
        val result = query.get().await()
        val networkChildsContainer = NetworkChildsContainer(result.toObjects(Child::class.java))
        networkChildsContainer.results.map { it.toDomainChild() }
    }

    suspend fun getChild(id: String): org.sic4change.nut4healthcentrotratamiento.data.entitities.Child = withContext(Dispatchers.IO) {
        val childRef = firestore.collection("childs")
        val query = childRef.whereEqualTo("id", id)
        val result = query.get().await()
        val networkChildsContainer = NetworkChildsContainer(result.toObjects(Child::class.java))
        networkChildsContainer.results[0].let {
            it.toDomainChild()
        }
    }

    suspend fun createChild(child: org.sic4change.nut4healthcentrotratamiento.data.entitities.Child) {
        withContext(Dispatchers.IO) {
            Timber.d("try to create child with firebase")
            try {
                val childsRef = firestore.collection("childs")
                val id = childsRef.add(child.toServerChild()).await().id
                childsRef.document(id).update("id", id,).await()
                Timber.d("Create child result: ok")
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
                val childsRef = firestore.collection("childs")
                childsRef.document(child.id).set(child.toServerChild()).await()
                Timber.d("update child result: ok")
            } catch (ex: Exception) {
                Timber.d("update child result: false ${ex.message}")
            }
        }
    }

    suspend fun getCases(childId: String): List<org.sic4change.nut4healthcentrotratamiento.data.entitities.Case> = withContext(Dispatchers.IO) {
        val casesRef = firestore.collection("cases")
        val query = casesRef.whereEqualTo("childId", childId).orderBy("lastdate", Query.Direction.DESCENDING )
        val result = query.get().await()
        val networkCasesContainer = NetworkCasesContainer(result.toObjects(Case::class.java))
        networkCasesContainer.results.map { it.toDomainCase() }
    }

    suspend fun createCase(case: org.sic4change.nut4healthcentrotratamiento.data.entitities.Case) {
        withContext(Dispatchers.IO) {
            Timber.d("try to create case with firebase")
            try {
                val childsRef = firestore.collection("childs")
                val query = childsRef.whereEqualTo("id", case.childId)
                val result = query.get().await()
                val networkChildsContainer = NetworkChildsContainer(result.toObjects(Child::class.java))
                networkChildsContainer.results[0].let {
                    val tutorId = it.toDomainChild().tutorId
                    val caseToUpload = org.sic4change.nut4healthcentrotratamiento.data.entitities.Case(
                        case.id, case.childId, tutorId, case.name, case.status, case.createdate,
                        case.lastdate, case.visits, case.observations)
                    val casesRef = firestore.collection("cases")
                    val id = casesRef.add(caseToUpload.toServerCase()).await().id
                    childsRef.document(id).update("id", id,).await()
                    Timber.d("Create case result: ok")
                }
            } catch (ex : Exception) {
                Timber.d("Create case result: false ${ex.message}")
            }
        }
    }

    suspend fun getCase(id: String): org.sic4change.nut4healthcentrotratamiento.data.entitities.Case = withContext(Dispatchers.IO) {
        val caseRef = firestore.collection("cases")
        val query = caseRef.whereEqualTo("id", id)
        val result = query.get().await()
        val networkCasesContainer = NetworkCasesContainer(result.toObjects(Case::class.java))
        networkCasesContainer.results[0].let {
            it.toDomainCase()
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
                val casesRef = firestore.collection("cases")
                casesRef.document(case.id).set(case.toServerCase()).await()
                Timber.d("update case result: ok")
            } catch (ex: Exception) {
                Timber.d("update case result: false ${ex.message}")
            }
        }
    }

    suspend fun checkDiagnosis(phone: String) {
        withContext(Dispatchers.IO) {
            val contractsRef = firestore.collection("contracts")
            val query = contractsRef.whereEqualTo("childPhoneContract", phone.filter { !it.isWhitespace() })
            val result = query.get().await()
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

    suspend fun checkTutor(phone: String): org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor? = withContext(Dispatchers.IO) {
        val tutorsRef = firestore.collection("tutors")
        val query = tutorsRef.whereEqualTo("phone", phone.filter { !it.isWhitespace() })
        val result = query.get().await()
        val networkTutorsContainer = NetworkTutorsContainer(result.toObjects(Tutor::class.java))
        try {
            networkTutorsContainer.results[0].let {
                it.toDomainTutor()
            }
        } catch (e : Exception) {
            null
        }
    }

    suspend fun getMalnutritionChildTable(): List<org.sic4change.nut4healthcentrotratamiento.data.entitities.MalNutritionChildTable> = withContext(Dispatchers.IO) {
        val malnutritionChildTableRef = firestore.collection("malnutritionChildTable")
        val query = malnutritionChildTableRef.whereEqualTo("createdby", "aasencio@sic4change.org")
        val result = query.get().await()
        val networkMalnutritionChildTableContainer = NetworkMalNutritionChildTableContainer(result.toObjects(MalNutritionChildTable::class.java))
        networkMalnutritionChildTableContainer.results.map { it.toDomainMalNutritionChildTable() }
    }

    suspend fun getMalnutritionTeenegerTable(): List<org.sic4change.nut4healthcentrotratamiento.data.entitities.MalNutritionTeenagerTable> = withContext(Dispatchers.IO) {
        val casesRef = firestore.collection("malnutritionTeenagersTable")
        val query = casesRef.whereEqualTo("createdby", "aasencio@sic4change.org")
        val result = query.get().await()
        val networkMalNutritionTeenagerTableContainer = NetworkMalNutritionTeenagerTableContainer(result.toObjects(MalNutritionTeenagerTable::class.java))
        networkMalNutritionTeenagerTableContainer.results.map { it.toDomainMalNutritionTeenagerTable() }
    }

    suspend fun getSymtom(): List<org.sic4change.nut4healthcentrotratamiento.data.entitities.Symtom> = withContext(Dispatchers.IO) {
        val casesRef = firestore.collection("symtoms")
        val query = casesRef.whereEqualTo("createdby", "aasencio@sic4change.org")
        val result = query.get().await()
        val networkSymtomContainer = NetworkSymtomContainer(result.toObjects(Symtom::class.java))
        networkSymtomContainer.results.map { it.toDomainSymtom() }
    }

    suspend fun getTreatments(): List<org.sic4change.nut4healthcentrotratamiento.data.entitities.Treatment> = withContext(Dispatchers.IO) {
        val casesRef = firestore.collection("treatments")
        val query = casesRef.whereEqualTo("active", true)
        val result = query.get().await()
        val networkTreatmentContainer = NetworkTreatmentContainer(result.toObjects(Treatment::class.java))
        networkTreatmentContainer.results.map { it.toDomainTreatment() }
    }

    suspend fun getVisits(caseId: String): List<org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit> = withContext(Dispatchers.IO) {
        val visitsRef = firestore.collection("visits")
        val query = visitsRef.whereEqualTo("caseId", caseId).orderBy("createdate", Query.Direction.DESCENDING )
        val result = query.get().await()
        val networkVisitsContainer = NetworkVisitContainer(result.toObjects(Visit::class.java))
        networkVisitsContainer.results.map { it.toDomainVisit() }
    }

    suspend fun getVisit(id: String): org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit = withContext(Dispatchers.IO) {
        val visitRef = firestore.collection("visits")
        val query = visitRef.whereEqualTo("id", id)
        val result = query.get().await()
        var symtoms = mutableListOf<String>()
        var treatments = mutableListOf<String>()
        val networkVisitContainer = NetworkVisitContainer(result.toObjects(Visit::class.java))
        networkVisitContainer.results[0].let { visit ->
            if (visit.symtoms.isNotEmpty()) {
                visit.symtoms.forEach {
                    val currentLocal = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
                    val symtomRef = firestore.collection("symtoms")
                    val query = symtomRef.whereEqualTo("id", it)
                    val result = query.get().await()
                    val networkSymtomContainer = NetworkSymtomContainer(result.toObjects(Symtom::class.java))
                    networkSymtomContainer.results[0].let {
                        when {
                            currentLocal.language.equals("es") -> {
                                symtoms.add(it.name)
                            }
                            currentLocal.language.equals("en") -> {
                                symtoms.add(it.name_en)
                            }
                            else -> {
                                symtoms.add(it.name_fr)
                            }
                        }
                    }
                }
                visit.symtoms = symtoms
            }
            if (visit.treatments.isNotEmpty()) {
                visit.treatments.forEach {
                    val currentLocal = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
                    val treatmentsRef = firestore.collection("treatments")
                    val query = treatmentsRef.whereEqualTo("id", it)
                    val result = query.get().await()
                    val networkTreatmentContainer = NetworkTreatmentContainer(result.toObjects(Treatment::class.java))
                    networkTreatmentContainer.results[0].let {
                        when {
                            currentLocal.language.equals("es") -> {
                                treatments.add(it.name)
                            }
                            currentLocal.language.equals("en") -> {
                                treatments.add(it.name_en)
                            }
                            else -> {
                                treatments.add(it.name_fr)
                            }
                        }
                    }
                }
                visit.treatments = treatments
            }
            visit.toDomainVisit()
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
                val casesRef = firestore.collection("cases")
                val queryCase = casesRef.whereEqualTo("id", visit.caseId)
                val resultCase = queryCase.get().await()
                val networkCasesContainer = NetworkCasesContainer(resultCase.toObjects(Case::class.java))
                networkCasesContainer.results[0].let { case ->
                    val visitToUpdate = org.sic4change.nut4healthcentrotratamiento.data.entitities.Visit("", case.id, case.childId, case.tutorId, visit.createdate,
                    visit.height, visit.weight, visit.imc, visit.armCircunference, visit.status, visit.measlesVaccinated,
                    visit.vitamineAVaccinated, visit.symtoms, visit.treatments, visit.observations)
                    val visitsRef = firestore.collection("visits")
                    val id = visitsRef.add(visitToUpdate.toServerVisit()).await().id
                    visitsRef.document(id).update("id", id,).await()
                    Timber.d("Create visit result: ok")
                    val caseRef = firestore.collection("cases")
                    val query = caseRef.whereEqualTo("id", visit.caseId)
                    val result = query.get().await()
                    val networkCaseContainer = NetworkCasesContainer(result.toObjects(Case::class.java))
                    networkCaseContainer.results[0].let { case ->
                        val visits = case.visits
                        caseRef.document(visit.caseId)
                            .update(
                                "visits", visits + 1,
                                "lastdate", Date()
                            ).await()
                    }

                }
            } catch (ex : Exception) {
                Timber.d("Create visit result: false ${ex.message}")
            }
        }
    }

    suspend fun checkDesnutrition(height: Double, weight: Double): Double = withContext(Dispatchers.IO) {
        var status = 0.0
        if (height > 120 && height <= 155) {
            val childMalNutritionTeenagerTableRef = firestore.collection("malnutritionTeenagersTable")
            val queryChildMalNutritionTeenagerTable = childMalNutritionTeenagerTableRef.whereEqualTo("sex", "mf").whereGreaterThanOrEqualTo("cm", (height - 0.1).toString())
                .orderBy("cm", Query.Direction.ASCENDING).limit(1)
            val resultChildMalNutritionTeenagerTable = queryChildMalNutritionTeenagerTable.get().await()
            val networkMalNutritionTeenagerTableContainer =
                NetworkMalNutritionTeenagerTableContainer(resultChildMalNutritionTeenagerTable.toObjects(MalNutritionTeenagerTable::class.java))
            networkMalNutritionTeenagerTableContainer.results[0].let { malNutritionTeenagerTable ->
                if (weight >= malNutritionTeenagerTable.hundred.toDouble()) {
                    status = 100.0
                } else if (weight > malNutritionTeenagerTable.eighty.toDouble()) {
                    status = 85.0
                } else if (weight > malNutritionTeenagerTable.seventy.toDouble()) {
                    status = 80.0
                } else  {
                    status = 70.0
                }
            }
        } else if (height >= 45) {
            val childMalNutritionChildTableRef = firestore.collection("malnutritionChildTable")
            val queryChildMalNutritionChildTable = childMalNutritionChildTableRef
                .whereGreaterThanOrEqualTo("cm", (height - 0.1).toString()).orderBy("cm", Query.Direction.ASCENDING).limit(1)
            val resultChildMalNutritionChildTable = queryChildMalNutritionChildTable.get().await()
            val networkMalNutritionChildTableContainer =
                NetworkMalNutritionChildTableContainer(resultChildMalNutritionChildTable.toObjects(MalNutritionChildTable::class.java))
            networkMalNutritionChildTableContainer.results[0].let { malNutritionChldTable ->
                if (weight > malNutritionChldTable.minusone.toDouble()) {
                    status = 0.0
                } else if (weight > malNutritionChldTable.minustwo.toDouble()) {
                    status = -1.0
                } else if (weight > malNutritionChldTable.minusthree.toDouble()) {
                    status = -1.5
                }  else  {
                    status = -3.0
                }
            }
        }
        status


        /*val casesRef = firestore.collection("cases")
        val queryCase = casesRef.whereEqualTo("id", caseId)
        val resultCase = queryCase.get().await()
        val networkCasesContainer = NetworkCasesContainer(resultCase.toObjects(Case::class.java))
        networkCasesContainer.results[0].let { case ->
            val childRef = firestore.collection("childs")
            val query = childRef.whereEqualTo("id", case.childId)
            val result = query.get().await()
            val networkChildContainer = NetworkChildsContainer(result.toObjects(Child::class.java))
            var status = 0.0
            networkChildContainer.results[0].let {
                val child = it.toDomainChild()
                val sex = child.sex
                if (height > 120) {
                    val childMalNutritionTeenagerTableRef = firestore.collection("malnutritionTeenagersTable")
                    val queryChildMalNutritionTeenagerTable = childMalNutritionTeenagerTableRef.whereEqualTo("sex", "mf").whereGreaterThanOrEqualTo("cm", height)
                        .orderBy("cm").limit(1)
                    val resultChildMalNutritionTeenagerTable = queryChildMalNutritionTeenagerTable.get().await()
                    val networkMalNutritionTeenagerTableContainer =
                        NetworkMalNutritionTeenagerTableContainer(resultChildMalNutritionTeenagerTable.toObjects(MalNutritionTeenagerTable::class.java))
                    networkMalNutritionTeenagerTableContainer.results[0].let { malNutritionTeenagerTable ->
                        if (weight >= malNutritionTeenagerTable.hundred.toDouble()) {
                            status = 100.0
                        } else if (weight >= malNutritionTeenagerTable.eightyfive.toDouble()) {
                            status = 85.0
                        } else if (weight >= malNutritionTeenagerTable.eighty.toDouble()) {
                            status = 80.0
                        } else  {
                            status = 70.0
                        }
                    }
                    status
                } else {
                    val childMalNutritionChildTableRef = firestore.collection("malnutritionChildTable")
                    val queryChildMalNutritionChildTable = childMalNutritionChildTableRef
                        .whereGreaterThanOrEqualTo("cm", height).orderBy("cm").limit(1)
                    val resultChildMalNutritionChildTable = queryChildMalNutritionChildTable.get().await()
                    val networkMalNutritionChildTableContainer =
                        NetworkMalNutritionChildTableContainer(resultChildMalNutritionChildTable.toObjects(MalNutritionChildTable::class.java))
                    networkMalNutritionChildTableContainer.results[0].let { malNutritionChldTable ->
                        if (weight >= malNutritionChldTable.zero.toDouble()) {
                            status = 0.0
                        } else if (weight >= malNutritionChldTable.minusone.toDouble()) {
                            status = -1.0
                        } else if (weight >= malNutritionChldTable.minusonefive.toDouble()) {
                            status = -1.5
                        } else if (weight >= malNutritionChldTable.minustwo.toDouble()) {
                            status = -2.0
                        } else  {
                            status = -3.0
                        }

                    }
                    status
                }
            }
        }*/

    }

}


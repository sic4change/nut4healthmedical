package org.sic4change.nut4healthcentrotratamiento.data.network

import com.google.firebase.firestore.Exclude
import com.squareup.moshi.JsonClass
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Derivation
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Item
import java.util.*

@JsonClass(generateAdapter = true)
data class NetworkUsersContainer(val results: List<User>)

@JsonClass(generateAdapter = true)
data class User(
    @Exclude val id: String = "",
    @Exclude val email: String = "",
    @Exclude val phone: String = "",
    @Exclude val role: String = "",
    @Exclude val username: String = "",
    @Exclude var photo: String? = "",
    @Exclude val point: String = ""
)

@JsonClass(generateAdapter = true)
data class NetworkPointsContainer(val results: List<Point>)

@JsonClass(generateAdapter = true)
data class Point(
    @Exclude val id: String = "",
    @Exclude val name: String = "",
    @Exclude val pointName: String = "",
    @Exclude val pointCode: String = "",
    @Exclude val fullName: String = "",
    @Exclude val type: String = "",
    @Exclude val phoneCode: String = "",
    @Exclude val phoneLength: Int = 0)

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
    @Exclude val maleRelation: String = "",
    @Exclude val womanStatus: String = "",
    @Exclude val weeks: Int = 0,
    @Exclude val childMinor: String = "",
    @Exclude  val babyAge: Int = 0,
    @Exclude val observations: String = "",
    @Exclude val active: Boolean = false,
    @Exclude val point: String? = ""
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
    @Exclude val brothers: Int = 0,
    @Exclude val code: String = "",
    @Exclude val createDate: Date = Date(),
    @Exclude val lastDate: Date = Date(),
    @Exclude val observations: String = "",
    @Exclude val point: String? = ""
)

@JsonClass(generateAdapter = true)
data class NetworkCasesContainer(val results: List<Case>)

@JsonClass(generateAdapter = true)
data class Case(
    @Exclude val id: String = "",
    @Exclude val childId: String? = "",
    @Exclude val fefaId: String? = "",
    @Exclude val tutorId: String = "",
    @Exclude val name: String = "",
    @Exclude val admissionType: String = "",
    @Exclude val admissionTypeServer: String = "",
    @Exclude val status: String = "",
    @Exclude val closedReason: String = "",
    @Exclude val createdate: Date = Date(),
    @Exclude val lastdate: Date = Date(),
    @Exclude val visits: Int = 0,
    @Exclude val observations: String = "",
    @Exclude val point: String? = ""
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
    @Exclude val cm: String = "",
    @Exclude val minusone: String = "",
    @Exclude val minusonefive: String = "",
    @Exclude val minusthree: String = "",
    @Exclude val minustwo: String = "",
    @Exclude val zero: String = "",
)

@JsonClass(generateAdapter = true)
data class NetworkMalNutritionTeenagerTableContainer(val results: List<MalNutritionTeenagerTable>)

@JsonClass(generateAdapter = true)
data class MalNutritionTeenagerTable(
    @Exclude val id: String = "",
    @Exclude val cm: String = "",
    @Exclude val eighty: String = "",
    @Exclude val eightyfive: String = "",
    @Exclude val hundred: String = "",
    @Exclude val seventy: String = "",
    @Exclude val sex: String = ""
)

@JsonClass(generateAdapter = true)
data class NetworkMalNutritionAdultTableContainer(val results: List<MalNutritionAdultTable>)

@JsonClass(generateAdapter = true)
data class MalNutritionAdultTable(
    @Exclude val cm: Double = 0.0,
    @Exclude val eighteen: Double = 0.0,
    @Exclude val eighteenfive: Double = 0.0,
    @Exclude val seventeen: Double = 0.0,
    @Exclude val seventeenfive: Double = 0.0,
    @Exclude val sixteen: Double = 0.0,
    @Exclude val sixteenfive: Double = 0.0,
)

@JsonClass(generateAdapter = true)
data class NetworkSymtomContainer(val results: List<Symtom>)

@JsonClass(generateAdapter = true)
data class Symtom(
    @Exclude val id: String = "",
    @Exclude val name: String = "",
    @Exclude val name_en: String = "",
    @Exclude val name_fr: String = "",
)

@JsonClass(generateAdapter = true)
data class NetworkTreatmentContainer(val results: List<Treatment>)

@JsonClass(generateAdapter = true)
data class Treatment(
    @Exclude val id: String = "",
    @Exclude val name: String = "",
    @Exclude val name_en: String = "",
    @Exclude val name_fr: String = "",
    @Exclude val active: Boolean = false,
    @Exclude val price: Double = 0.0,
)

@JsonClass(generateAdapter = true)
data class NetworkComplicationContainer(val results: List<Complication>)

@JsonClass(generateAdapter = true)
data class Complication(
    @Exclude val id: String = "",
    @Exclude val name: String = "",
    @Exclude val name_en: String = "",
    @Exclude val name_fr: String = "",
    @Exclude val active: Boolean = false,
)

@JsonClass(generateAdapter = true)
data class NetworkVisitContainer(val results: List<Visit>)

@JsonClass(generateAdapter = true)
data class Visit(
    @Exclude val id: String = "",
    @Exclude val caseId: String = "",
    @Exclude val childId: String? = "",
    @Exclude val fefaId: String? = "",
    @Exclude val tutorId: String = "",
    @Exclude val createdate: Date = Date(),
    @Exclude val height: Double = 0.0,
    @Exclude val weight: Double = 0.0,
    @Exclude val imc: Double = 0.0,
    @Exclude val armCircunference: Double = 0.0,
    @Exclude val status: String = "",
    @Exclude val edema: String = "",
    @Exclude val respiratonStatus: String = "",
    @Exclude val appetiteTest: String = "",
    @Exclude val infection: String = "",
    @Exclude val eyesDeficiency: String = "",
    @Exclude val deshidratation: String = "",
    @Exclude val vomiting: String = "",
    @Exclude val diarrhea: String = "",
    @Exclude val fever: String = "",
    @Exclude val cough: String = "",
    @Exclude val temperature: String = "",
    @Exclude val vitamineAVaccinated: String = "",
    @Exclude val acidfolicAndFerroVaccinated: String = "",
    @Exclude val vaccinationCard: String = "",
    @Exclude val rubeolaVaccinated: String = "",
    @Exclude val amoxicilina: String = "",
    @Exclude val otherTratments: String = "",
    @Exclude var complications: List<Complication> = emptyList(),
    @Exclude var observations: String = "",
    @Exclude var point: String? = ""
)

@JsonClass(generateAdapter = true)
data class NetworkDerivationContainer(val results: List<Derivation>)

@JsonClass(generateAdapter = true)
data class Derivation(
    @Exclude val id: String = "",
    @Exclude val caseId: String = "",
    @Exclude val originId: String = "",
    @Exclude val destinationId: String = "",
    @Exclude val childId: String? = "",
    @Exclude val fefaId: String? = "",
    @Exclude val createdate: Date = Date()
)









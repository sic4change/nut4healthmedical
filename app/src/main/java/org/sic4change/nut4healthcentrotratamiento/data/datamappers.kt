package org.sic4change.nut4healthcentrotratamiento.data


import org.sic4change.nut4healthcentrotratamiento.data.entitities.*
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Contract
import org.sic4change.nut4healthcentrotratamiento.data.entitities.MalNutritionChildTable
import org.sic4change.nut4healthcentrotratamiento.data.entitities.MalNutritionTeenagerTable
import java.util.Date
import org.sic4change.nut4healthcentrotratamiento.data.network.User as ServerUser
import org.sic4change.nut4healthcentrotratamiento.data.network.Point as ServerPoint
import org.sic4change.nut4healthcentrotratamiento.data.network.Tutor as ServerTutor
import org.sic4change.nut4healthcentrotratamiento.data.network.Child as ServerChild
import org.sic4change.nut4healthcentrotratamiento.data.network.Case as ServerCase
import org.sic4change.nut4healthcentrotratamiento.data.network.Contract as ServerContract
import org.sic4change.nut4healthcentrotratamiento.data.network.MalNutritionChildTable as ServerMalNutritionChildTable
import org.sic4change.nut4healthcentrotratamiento.data.network.MalNutritionTeenagerTable as ServerMalNutritionTeenagerTable
import org.sic4change.nut4healthcentrotratamiento.data.network.Symtom as ServerSymtom
import org.sic4change.nut4healthcentrotratamiento.data.network.Treatment as ServerTreatment
import org.sic4change.nut4healthcentrotratamiento.data.network.Complication as ServerComplication
import org.sic4change.nut4healthcentrotratamiento.data.network.Visit as ServerVisit
import org.sic4change.nut4healthcentrotratamiento.data.network.Derivation as ServerDerivation

fun ServerUser.toDomainUser() : User = User(
    id, email, phone, role, username, point, photo
)

fun ServerPoint.toDomainPoint() : Point = Point(
    id, name, pointName, pointCode,  fullName, phoneCode, type, phoneLength
)

fun ServerTutor.toDomainTutor() : Tutor = Tutor(
    id, name, surnames, sex, ethnicity, birthdate, phone, address, createDate, lastDate, maleRelation,
    womanStatus, weeks.toString(), childMinor, babyAge.toString(), observations, active, point
)

fun Tutor.toServerTutor() : ServerTutor = ServerTutor(
    id, name, surnames, sex, ethnicity, birthdate, phone, address, createDate, lastDate, maleRelation,
    womanStatus, weeks.toInt(), childMinor, babyAge.toInt(), observations, active, point
)

fun ServerChild.toDomainChild() : Child = Child(
    id, tutorId, name, surnames, sex, ethnicity, birthdate, brothers, code, createDate, lastDate, observations, point
)

fun Child.toServerChild() : ServerChild = ServerChild(
    id, tutorId, name, surnames, sex, ethnicity, birthdate, brothers, code, createDate, lastDate, observations, point
)

fun ServerCase.toDomainCase() : Case = Case(
    id, childId, fefaId, tutorId, name, admissionType, admissionTypeServer, status, closedReason,  createdate, lastdate, visits.toString(), observations, point
)

fun Case.toServerCase() : ServerCase = ServerCase(
    id, childId, fefaId, tutorId, name, admissionType, admissionTypeServer, status, closedReason, createdate,
    lastdate, visits.toInt(), observations, point
)

fun ServerContract.toDomainContract() : Contract = Contract(
    id, status, medicalDate, medicalDateMiliseconds, medicalDateToUpdate, medicalDateToUpdateInMilis
)

fun Contract.toServerContract() : ServerContract = ServerContract(
    id, status, medicalDate, medicalDateMiliseconds, medicalDateToUpdate, medicalDateToUpdateInMilis
)

fun ServerMalNutritionChildTable.toDomainMalNutritionChildTable() : MalNutritionChildTable = MalNutritionChildTable(
    id, cm, minusone, minusonefive, minusthree, minustwo, zero
)

fun MalNutritionChildTable.toServerMalNutritionChildTable() : ServerMalNutritionChildTable = ServerMalNutritionChildTable(
    id, cm, minusone, minusonefive, minusthree, minustwo, zero
)

fun ServerMalNutritionTeenagerTable.toDomainMalNutritionTeenagerTable() : MalNutritionTeenagerTable = MalNutritionTeenagerTable(
    id, cm, eighty, eightyfive, hundred, seventy, sex
)

fun MalNutritionTeenagerTable.toServerMalNutritionTeenagerTable() : ServerMalNutritionTeenagerTable = ServerMalNutritionTeenagerTable(
    id, cm, eighty, eightyfive, hundred, seventy, sex
)

fun ServerSymtom.toDomainSymtom() : Symtom = Symtom(
    id, name, name_en, name_fr, false
)

fun Symtom.toServerSymtom() : ServerSymtom = ServerSymtom(
    id, name, name_en, name_fr
)

fun ServerTreatment.toDomainTreatment() : Treatment = Treatment(
    id, name, name_en, name_fr, active, price, false
)

fun Treatment.toServerTreatment() : ServerTreatment = ServerTreatment(
    id, name, name_en, name_fr, active, price
)

fun ServerComplication.toDomainComplication() : Complication = Complication(
    id, name, name_en, name_fr, false
)

fun Complication.toServerComplication() : ServerComplication = ServerComplication(
    id, name, name_en, name_fr
)

fun ServerVisit.toDomainVisit() : Visit {
    return Visit(id, caseId, childId, fefaId, tutorId, createdate, height, weight, imc, armCircunference,
        status, edema, respiratonStatus, appetiteTest, infection, eyesDeficiency, deshidratation,
        vomiting, diarrhea, fever, cough, temperature,vitamineAVaccinated, acidfolicAndFerroVaccinated,
        vaccinationCard, rubeolaVaccinated, amoxicilina, otherTratments,
        complications.map { it.toDomainComplication() }.toMutableList(), observations, point)
}

fun Visit.toServerVisit() : ServerVisit  {
    return ServerVisit(id, caseId, childId, fefaId, tutorId, createdate, height, weight, imc, armCircunference,
        status, edema, respiratonStatus, appetiteTest, infection, eyesDeficiency, deshidratation,
        vomiting, diarrhea, fever, cough, temperature,vitamineAVaccinated, acidfolicAndFerroVaccinated,
        vaccinationCard, rubeolaVaccinated,  amoxicilina, otherTratments,
        complications.map { it.toServerComplication() }, observations, point)
}

fun ServerDerivation.toDomainDerivation() : Derivation {
    return Derivation(id, originId, destinationId, childId, fefaId, createdate)
}

fun Derivation.toServerDerivation() : ServerDerivation  {
    return ServerDerivation(id, originId, destinationId, childId, fefaId, createdate)
}









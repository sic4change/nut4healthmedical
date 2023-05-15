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
import org.sic4change.nut4healthcentrotratamiento.data.network.Visit as ServerVisit

fun ServerUser.toDomainUser() : User = User(
    id, email, role, username, point, photo
)

fun ServerPoint.toDomainPoint() : Point = Point(
    id, name, fullName, phoneCode, phoneLength
)


fun ServerTutor.toDomainTutor() : Tutor = Tutor(
    id, name, surnames, sex, ethnicity, birthdate, phone, address, createDate, lastDate, maleRelation,
    womanStatus, weeks.toString(), childMinor, armCircunference, babyAge, status, observations, active, point
)

fun Tutor.toServerTutor() : ServerTutor = ServerTutor(
    id, name, surnames, sex, ethnicity, birthdate, phone, address, createDate, lastDate, maleRelation,
    womanStatus, weeks.toInt(), childMinor, armCircunference, babyAge, status, observations, active, point
)

fun ServerChild.toDomainChild() : Child = Child(
    id, tutorId, name, surnames, sex, ethnicity, birthdate, brothers, code, createDate, lastDate, observations, point
)

fun Child.toServerChild() : ServerChild = ServerChild(
    id, tutorId, name, surnames, sex, ethnicity, birthdate, brothers, code, createDate, lastDate, observations, point
)

fun ServerCase.toDomainCase() : Case = Case(
    id, childId, tutorId, name, status, createdate, lastdate, visits.toString(), observations, point
)

fun Case.toServerCase() : ServerCase = ServerCase(
    id, childId, tutorId, name, status, createdate, lastdate, visits.toInt(), observations, point
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

fun ServerVisit.toDomainVisit() : Visit {
    return Visit(id, caseId, childId, tutorId, createdate, height, weight, imc, armCircunference, status,
        measlesVaccinated, vitamineAVaccinated, symtoms.map { it.toDomainSymtom() }.toMutableList(),
        treatments.map { it.toDomainTreatment() }.toMutableList(), observations, point)
}

fun Visit.toServerVisit() : ServerVisit  {
    return ServerVisit(id, caseId, childId, tutorId, createdate, height, weight, imc, armCircunference, status,
        measlesVaccinated, vitamineAVaccinated, symtoms.map { it.toServerSymtom() },
        treatments.map { it.toServerTreatment() }, observations, point)
}









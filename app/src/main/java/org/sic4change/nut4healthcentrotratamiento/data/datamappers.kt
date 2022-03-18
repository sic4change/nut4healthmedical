package org.sic4change.nut4healthcentrotratamiento.data

import com.google.firebase.firestore.Exclude
import org.sic4change.nut4healthcentrotratamiento.data.entitities.*
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Contract
import java.util.*
import org.sic4change.nut4healthcentrotratamiento.data.network.User as ServerUser
import org.sic4change.nut4healthcentrotratamiento.data.network.Tutor as ServerTutor
import org.sic4change.nut4healthcentrotratamiento.data.network.Child as ServerChild
import org.sic4change.nut4healthcentrotratamiento.data.network.Case as ServerCase
import org.sic4change.nut4healthcentrotratamiento.data.network.Contract as ServerContract


fun ServerUser.toDomainUser() : User = User(
    id, email, role, username
)

fun ServerTutor.toDomainTutor() : Tutor = Tutor(
    id, name, surnames, sex, ethnicity, birthdate, phone, address, createDate, lastDate, pregnant, observations, weeks.toString(), active
)

fun Tutor.toServerTutor() : ServerTutor = ServerTutor(
    id, name, surnames, sex, ethnicity, birthdate, phone, address, createDate, lastDate, pregnant, observations, weeks.toInt(), active
)

fun ServerChild.toDomainChild() : Child = Child(
    id, tutorId, name, surnames, sex, ethnicity, birthdate, createDate, lastDate, observations
)

fun Child.toServerChild() : ServerChild = ServerChild(
    id, tutorId, name, surnames, sex, ethnicity, birthdate, createDate, lastDate, observations
)

fun ServerCase.toDomainCase() : Case = Case(
    id, childId, tutorId, name, status, createdate, lastdate, visits.toString(), observations
)

fun Case.toServerCase() : ServerCase = ServerCase(
    id, childId, tutorId, name, status, createdate, lastdate, visits.toInt(), observations
)

fun ServerContract.toDomainContract() : Contract = Contract(
    id, status, medicalDate, medicalDateMiliseconds, medicalDateToUpdate, medicalDateToUpdateInMilis
)

fun Contract.toServerContract() : ServerContract = ServerContract(
    id, status, medicalDate, medicalDateMiliseconds, medicalDateToUpdate, medicalDateToUpdateInMilis
)





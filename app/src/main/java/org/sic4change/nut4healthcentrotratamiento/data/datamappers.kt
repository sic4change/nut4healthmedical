package org.sic4change.nut4healthcentrotratamiento.data


import io.grpc.Server
import org.sic4change.nut4healthcentrotratamiento.data.entitities.Tutor
import org.sic4change.nut4healthcentrotratamiento.data.entitities.User
import org.sic4change.nut4healthcentrotratamiento.data.network.User as ServerUser
import org.sic4change.nut4healthcentrotratamiento.data.network.Tutor as ServerTutor



fun ServerUser.toDomainUser() : User = User(
    id, email, role, username
)

fun ServerTutor.toDomainTutor() : Tutor = Tutor(
    id, name, surnames, sex, ethnicity, birthdate, phone, address, createDate, lastDate, pregnant, observations, weeks.toString(), active
)

fun Tutor.toServerTutor() : ServerTutor = ServerTutor(
    id, name, surnames, sex, ethnicity, birthdate, phone, address, createDate, lastDate, pregnant, observations, weeks.toInt(), active
)



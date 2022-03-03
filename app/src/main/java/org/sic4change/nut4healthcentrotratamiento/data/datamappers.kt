package org.sic4change.nut4healthcentrotratamiento.data


import org.sic4change.nut4healthcentrotratamiento.data.entitities.User
import org.sic4change.nut4healthcentrotratamiento.data.network.User as ServerUser




fun ServerUser.toDomainUser() : User = User(
    id, email, role, username
)


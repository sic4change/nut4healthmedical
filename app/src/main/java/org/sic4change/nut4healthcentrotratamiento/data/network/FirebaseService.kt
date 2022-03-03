package org.sic4change.nut4healthcentrotratamiento.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

object NUT4HealthFirebaseService {
    val fbAuth = FirebaseAuth.getInstance()
    val mFirestore = FirebaseFirestore.getInstance()
    val mStorage = FirebaseStorage.getInstance()
}
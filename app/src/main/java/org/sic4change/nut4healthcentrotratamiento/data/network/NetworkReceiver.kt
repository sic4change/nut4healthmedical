package org.sic4change.nut4healthcentrotratamiento.data.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.firestore.Source
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NetworkReceiver : BroadcastReceiver(), NetworkUtils.NetworkChangeListener {

    var context: Context? = null

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("NetworkReceiver", "onReceive called")
        this.context = context
        NetworkUtils.registerNetworkChangeListener(context, this)
    }

    override fun onNetworkChange(isConnected: Boolean) {
        Log.d("NetworkReceiver", "onNetworkChange called: $isConnected")
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            setFirestoreSource()
        }
    }

    private suspend fun setFirestoreSource() {
        Log.d("NetworkReceiver", "setFirestoreSource called")
        if (context != null)  {
            val isConnected = NetworkUtils.isInternetAvailable(context!!)
            FirebaseDataSource.setFirestoreSource(isConnected)
        }

    }

}
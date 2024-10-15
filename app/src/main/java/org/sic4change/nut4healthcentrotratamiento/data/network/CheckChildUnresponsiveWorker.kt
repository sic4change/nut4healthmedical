package org.sic4change.nut4healthcentrotratamiento.data.network

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import java.util.Calendar

class CheckChildUnresponsiveWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val user = FirebaseDataSource.getUser()
            if (user != null) {
                FirebaseDataSource.checkChildUnresponsive(user.point!!)
                Timber.d("Checking cases unresponsive")
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}

fun scheduleDailyCheckUnresponsive(context: Context) {
    val currentDate = Calendar.getInstance()
    val dueDate = Calendar.getInstance()

    dueDate.set(Calendar.HOUR_OF_DAY, 0)
    dueDate.set(Calendar.MINUTE, 1)
    dueDate.set(Calendar.SECOND, 0)

    if (dueDate.before(currentDate)) {
        dueDate.add(Calendar.HOUR_OF_DAY, 24)
    }

    val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis

    val dailyWorkRequest = OneTimeWorkRequestBuilder<CheckChildUnresponsiveWorker>()
        .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
        .addTag("DailyCheck")
        .build()

    WorkManager.getInstance(context).enqueue(dailyWorkRequest)
}
package com.anuar.movieapp.data.worker

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.anuar.movieapp.data.MyBroadcastReceiver
import com.anuar.movieapp.data.database.MoviesDao
import com.anuar.movieapp.data.loadDataAndRefreshDatabase
import com.anuar.movieapp.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val application: Application,
    private val dao: MoviesDao,
    private val apiService: ApiService
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val result = loadDataAndRefreshDatabase(application,dao, apiService)
            if (result) {
                val intent = Intent(applicationContext, MyBroadcastReceiver::class.java)
                applicationContext.sendBroadcast(intent)
                Result.success()
            } else {
                Result.retry()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val NAME = "RefreshDataWorker"

        fun makeRequest(): PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<RefreshDataWorker>(15, TimeUnit.MINUTES).build()
    }

    class Factory @Inject constructor(
        private val application: Application,
        private val dao: MoviesDao,
        private val apiService: ApiService
    ):RefreshDataWorkerFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return RefreshDataWorker(context,workerParameters, application,dao,apiService)
        }
    }
}

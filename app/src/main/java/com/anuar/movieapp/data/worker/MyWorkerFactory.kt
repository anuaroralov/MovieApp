package com.anuar.movieapp.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Provider
import javax.inject.Inject

class MyWorkerFactory @Inject constructor(
    private val workerProviders: @JvmSuppressWildcards Map<Class<out ListenableWorker>, Provider<RefreshDataWorkerFactory>>
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            RefreshDataWorker::class.qualifiedName -> {
                val refreshDataWorkerFactory=workerProviders[RefreshDataWorker::class.java]?.get()
                return refreshDataWorkerFactory?.create(appContext,workerParameters)
            }

            else -> null
        }
    }
}
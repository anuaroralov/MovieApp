package com.anuar.movieapp.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface RefreshDataWorkerFactory {

    fun create(
        context:Context,
        workerParameters: WorkerParameters
    ):ListenableWorker
}
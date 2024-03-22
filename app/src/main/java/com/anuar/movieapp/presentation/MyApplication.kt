package com.anuar.movieapp.presentation

import android.app.Application
import androidx.work.Configuration
import com.anuar.movieapp.data.worker.MyWorkerFactory
import com.anuar.movieapp.di.DaggerApplicationComponent
import javax.inject.Inject

class MyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: MyWorkerFactory

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
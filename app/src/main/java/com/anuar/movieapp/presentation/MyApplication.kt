package com.anuar.movieapp.presentation

import android.app.Application
import com.anuar.movieapp.di.DaggerApplicationComponent


class MyApplication : Application() {



    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}
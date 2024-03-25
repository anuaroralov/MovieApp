package com.anuar.movieapp.di

import android.app.Application
import com.anuar.movieapp.presentation.Fragments.DetailFragment
import com.anuar.movieapp.presentation.Fragments.FavouritesFragment
import com.anuar.movieapp.presentation.Fragments.HomeFragment
import com.anuar.movieapp.presentation.MyApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DomainModule::class, DataModule::class, ViewModelModule::class, WorkerModule::class])
interface ApplicationComponent {

    fun inject(fragment: HomeFragment)

    fun inject(fragment: DetailFragment)

    fun inject(fragment: FavouritesFragment)

    fun inject(application: MyApplication)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}
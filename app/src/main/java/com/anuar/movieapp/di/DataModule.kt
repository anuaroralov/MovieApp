package com.anuar.movieapp.di

import android.app.Application
import com.anuar.movieapp.data.database.AppDatabase
import com.anuar.movieapp.data.database.MoviesDao
import com.anuar.movieapp.data.network.ApiFactory
import com.anuar.movieapp.data.network.ApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideMoviesDao(application: Application): MoviesDao {
        return AppDatabase.getInstance(application).moviesDao()
    }

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return ApiFactory.apiService
    }
}
package com.anuar.movieapp.di

import com.anuar.movieapp.data.network.ApiFactory
import com.anuar.movieapp.data.network.ApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return ApiFactory.apiService
    }
}
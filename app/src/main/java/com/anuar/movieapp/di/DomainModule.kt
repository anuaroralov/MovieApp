package com.anuar.movieapp.di

import com.anuar.movieapp.data.RepositoryImpl
import com.anuar.movieapp.domain.Repository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DomainModule {

    @Singleton
    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository
}
package com.anuar.movieapp.di

import androidx.lifecycle.ViewModel
import com.anuar.movieapp.presentation.MyViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(MyViewModel::class)
    @Binds
    fun bindMyViewModel(impl:MyViewModel): ViewModel
}
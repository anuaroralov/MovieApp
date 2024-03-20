package com.anuar.movieapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.anuar.movieapp.data.RepositoryImpl
import com.anuar.movieapp.domain.GetMovieCategoryListUseCase

class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)

    private val getMovieCategoryListUseCase = GetMovieCategoryListUseCase(repository)

    private val networkState = NetworkLiveData(application.applicationContext)


    val movieCategoriesList = getMovieCategoryListUseCase()


    init {
        repository.loadData()
    }

    override fun onCleared() {
        super.onCleared()
        networkState.removeObserver {}
    }
}
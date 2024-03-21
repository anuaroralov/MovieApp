package com.anuar.movieapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.anuar.movieapp.data.RepositoryImpl
import com.anuar.movieapp.domain.GetMovieCategoryListUseCase
import com.anuar.movieapp.domain.LoadDataUseCase

class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)

    private val getMovieCategoryListUseCase = GetMovieCategoryListUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    private val networkState = NetworkLiveData(application.applicationContext)


    val movieCategoriesList = getMovieCategoryListUseCase()


    init {
//        networkState.observeForever { isConnected ->
//            if (isConnected) {
        loadDataUseCase()
//            }
//        }
    }

    override fun onCleared() {
        super.onCleared()
        networkState.removeObserver {}
    }
}
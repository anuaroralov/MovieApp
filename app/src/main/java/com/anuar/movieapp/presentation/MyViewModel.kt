package com.anuar.movieapp.presentation

import androidx.lifecycle.ViewModel
import com.anuar.movieapp.domain.GetMovieCategoryListUseCase
import com.anuar.movieapp.domain.LoadDataUseCase
import javax.inject.Inject

class MyViewModel @Inject constructor(
    private val getMovieCategoryListUseCase: GetMovieCategoryListUseCase,
    private val loadDataUseCase: LoadDataUseCase,
    private val networkState: NetworkLiveData
) : ViewModel() {

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
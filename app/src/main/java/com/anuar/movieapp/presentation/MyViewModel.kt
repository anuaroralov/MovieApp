package com.anuar.movieapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anuar.movieapp.data.RepositoryImpl
import com.anuar.movieapp.domain.GetMovieCategoryListUseCase
import com.anuar.movieapp.domain.GetMovieListUseCase
import com.anuar.movieapp.domain.MovieCategory
import kotlinx.coroutines.launch

class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RepositoryImpl()

    private val getMovieCategoryListUseCase = GetMovieCategoryListUseCase(repository)

    private val getMovieListUseCase=GetMovieListUseCase(repository)

    private val networkState = NetworkLiveData(application.applicationContext)


    private val _movieCategoriesList = MutableLiveData<List<MovieCategory>>()
    val movieCategoriesList: LiveData<List<MovieCategory>>
        get() = _movieCategoriesList

    init {
        networkState.observeForever { isConnected ->
            if (isConnected) {
                initList()
            }
        }
    }

    private fun initList() {
        viewModelScope.launch {
            _movieCategoriesList.value = getMovieCategoryListUseCase()
        }
    }

//    suspend fun loadMoviesForCategory(category: MovieCategory) {
//        if (category.currentPage==category.totalPages) return
//
//        try {
//            val nextPage = category.currentPage+1
//            val newMoviesResponse = getMovieListUseCase.run(category.id, nextPage)
//
//            if (newMoviesResponse.isNotEmpty()) {
//                category.movies.addAll(newMoviesResponse)
//                category.currentPage = nextPage
//            }
//        } catch (e: Exception) {
//            // Обработка ошибок, например, показ сообщения пользователю
//        }
//    }


    override fun onCleared() {
        super.onCleared()
        networkState.removeObserver {}
    }
}
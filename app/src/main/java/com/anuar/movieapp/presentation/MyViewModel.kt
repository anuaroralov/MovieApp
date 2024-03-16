package com.anuar.movieapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anuar.movieapp.data.RepositoryImpl
import com.anuar.movieapp.domain.GetMovieListUseCase
import com.anuar.movieapp.domain.Movie
import kotlinx.coroutines.launch

class MyViewModel(application: Application):AndroidViewModel(application) {

    private val repository=RepositoryImpl()

    private val getMovieListUseCase=GetMovieListUseCase(repository)

    private val networkState = NetworkLiveData(application.applicationContext)


    private val _movieList= MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() =_movieList

    init {
        networkState.observeForever { isConnected ->
            if (isConnected) {
                initList()
            }
        }
    }

    private fun initList(){
        viewModelScope.launch{
            _movieList.value=getMovieListUseCase()
        }
    }

    override fun onCleared() {
        super.onCleared()
        networkState.removeObserver{}
    }
}
package com.anuar.movieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anuar.movieapp.data.network.model.MovieDto

class MyViewModel():ViewModel() {

    private val _movieList = MutableLiveData<List<MovieDto>>()
    val movieList:LiveData<List<MovieDto>>
        get() =_movieList

    init {
//        initList()
    }

//    fun initList(){
//        viewModelScope.launch{
//            _movieList.value=apiService.moviesList()
//        }
//    }
}
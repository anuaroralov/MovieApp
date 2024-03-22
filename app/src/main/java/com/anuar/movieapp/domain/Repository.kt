package com.anuar.movieapp.domain

import androidx.lifecycle.LiveData

interface Repository {

    fun getMovieCategoryList(): LiveData<List<MovieCategory>>

    fun loadData()

}
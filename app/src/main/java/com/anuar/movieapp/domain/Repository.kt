package com.anuar.movieapp.domain

import androidx.lifecycle.LiveData

interface Repository {

    fun getMovieCategoryList(): LiveData<List<MovieCategory>>

    fun loadData()

    suspend fun refreshData(): Boolean

    fun getFavouriteMovieList():LiveData<List<Movie>>

    suspend fun updateFavouriteStatus(id:Int)

}
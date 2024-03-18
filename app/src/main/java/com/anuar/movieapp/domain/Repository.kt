package com.anuar.movieapp.domain

interface Repository {

    suspend fun getMovieCategoryList(): List<MovieCategory>

    suspend fun getMovieList(listId:Int,page:Int):List<Movie>
}
package com.anuar.movieapp.domain

interface Repository {

    suspend fun getMovieCategoryList(): List<MovieCategory>
}
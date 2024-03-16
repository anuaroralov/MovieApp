package com.anuar.movieapp.domain

interface Repository {

    suspend fun getMovieList(): List<Movie>
}
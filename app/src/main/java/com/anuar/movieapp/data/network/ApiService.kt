package com.anuar.movieapp.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/{movie_id}")
    suspend fun movieDetail (
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): MovieDto

    @GET("movie/popular?api_key=856d1bb49ba36c1a4df9bcd74a9e41eb")
    suspend fun moviesList():ListOfMovies
}
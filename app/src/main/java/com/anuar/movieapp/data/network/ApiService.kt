package com.anuar.movieapp.data.network

import com.anuar.movieapp.data.network.model.ListOfMovies
import com.anuar.movieapp.data.network.model.MovieDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/{movie_id}")
    suspend fun movieDetail(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ): MovieDto

    @GET("list/{list_id}")
    suspend fun moviesList(
        @Path("list_id") listId: Int,
    ): ListOfMovies
}
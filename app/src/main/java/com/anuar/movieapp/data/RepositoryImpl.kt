package com.anuar.movieapp.data

import com.anuar.movieapp.data.network.ApiFactory
import com.anuar.movieapp.domain.Movie
import com.anuar.movieapp.domain.Repository

class RepositoryImpl():Repository {

    private val apiService = ApiFactory.apiService

    private val mapper = Mapper()
    override suspend fun getMovieList(): List<Movie> {
        return try {
            apiService.moviesList().results.map { movieDto ->
                mapper.mapDtoModelToEntity(movieDto)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }


}
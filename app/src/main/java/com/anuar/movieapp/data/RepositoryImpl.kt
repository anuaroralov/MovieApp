package com.anuar.movieapp.data

import com.anuar.movieapp.data.network.ApiFactory
import com.anuar.movieapp.domain.MovieCategory
import com.anuar.movieapp.domain.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class RepositoryImpl() : Repository {

    private val apiService = ApiFactory.apiService

    override suspend fun getMovieCategoryList(): List<MovieCategory> {
        val listIds = listOf(1, 2, 3, 4, 5)

        val movieCategories = coroutineScope {
            listIds.map { listId ->
                async { apiService.moviesList(listId) }
            }.awaitAll().map { listOfMovies ->
                listOfMovies.mapToEntity()
            }
        }

        return movieCategories
    }


}
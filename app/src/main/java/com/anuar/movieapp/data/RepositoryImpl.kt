package com.anuar.movieapp.data

import com.anuar.movieapp.data.network.ApiFactory
import com.anuar.movieapp.domain.Movie
import com.anuar.movieapp.domain.MovieCategory
import com.anuar.movieapp.domain.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class RepositoryImpl() : Repository {

    private val apiService = ApiFactory.apiService

    override suspend fun getMovieCategoryList(): List<MovieCategory> {
        val listIds = (1..50).toList()

        val movieCategories = coroutineScope {
            listIds.mapNotNull { listId ->
                async {
                    try {
                        apiService.moviesList(listId,1)
                    } catch (e: Exception) {
                        null
                    }
                }
            }.awaitAll().filterNotNull().filter { listOfMovies ->
                listOfMovies.items.isNotEmpty()
            }.map { listOfMovies ->
                listOfMovies.mapToEntity()
            }
        }
        return movieCategories
    }

    override suspend fun getMovieList(listId:Int,page:Int):List<Movie>{
        return try {
            apiService.moviesList(listId,page).items.map { movieDto ->
                movieDto.mapToEntity()
            }
        } catch (e: Exception) {
            throw e
        }
    }


}
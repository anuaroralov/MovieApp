package com.anuar.movieapp.data

import com.anuar.movieapp.data.network.ApiService
import com.anuar.movieapp.domain.MovieCategory
import kotlinx.coroutines.*

suspend fun loadData(apiService: ApiService): List<MovieCategory> {

    val movieCategories = mutableListOf<MovieCategory>()

    try {
        coroutineScope {
            (1..50).map { listId ->
                async(Dispatchers.IO) {
                    try {
                        val listOfMovies = apiService.moviesList(listId, 1)
                        if (listOfMovies.items.isNotEmpty()) {
                            val movieCategory = listOfMovies.toEntity()
                            synchronized(movieCategories) {
                                movieCategories.add(movieCategory)
                            }
                        }
                    } catch (e: Exception) {
                        throw e
                    }
                }
            }.awaitAll()
        }
    } catch (e: Exception) {
        return emptyList()
    }

    return movieCategories
}

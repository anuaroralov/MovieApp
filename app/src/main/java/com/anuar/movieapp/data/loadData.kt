package com.anuar.movieapp.data

import com.anuar.movieapp.data.database.MovieCategoryDbModel
import com.anuar.movieapp.data.database.MovieDbModel
import com.anuar.movieapp.data.database.MoviesDao
import com.anuar.movieapp.data.network.ApiService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

suspend fun loadDataAndRefreshDatabase(dao: MoviesDao, apiService: ApiService): Boolean {
    try {
//        dao.clearMovieCategoriesTable()
//        dao.clearMoviesTable()
        val listIds = (1..50).toList()
        val allMovieCategories = mutableListOf<MovieCategoryDbModel>()
        val allMoviesWithCategoryIds = mutableListOf<MovieDbModel>()

        coroutineScope {
            listIds.map { listId ->
                async {
                    try {
                        val listOfMovies = apiService.moviesList(listId, 1)
                        if (listOfMovies.items.isNotEmpty()) {
                            val movieCategories = listOfMovies.mapToDbModel()

                            allMovieCategories.add(movieCategories.category)
                            allMoviesWithCategoryIds.addAll(movieCategories.movies.map { movie ->
                                movie.copy(categoryId = movieCategories.category.id)
                            })
                        }
                    } catch (e: Exception) {

                    }
                }
            }.awaitAll()
        }

        if (allMovieCategories.isNotEmpty() && allMoviesWithCategoryIds.isNotEmpty()) {
            val favouriteMovieIds = dao.getFavoriteMovieIds()
            dao.updateDatabase(allMovieCategories, allMoviesWithCategoryIds)
            favouriteMovieIds.forEach { dao.updateFavouriteStatus(it) }
            return true
        }
    } catch (e: Exception) {

    }
    return false
}

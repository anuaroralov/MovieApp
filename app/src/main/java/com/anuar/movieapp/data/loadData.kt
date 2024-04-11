package com.anuar.movieapp.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.anuar.movieapp.data.database.MovieCategoryDbModel
import com.anuar.movieapp.data.database.MovieDbModel
import com.anuar.movieapp.data.database.MoviesDao
import com.anuar.movieapp.data.network.ApiService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

suspend fun loadDataAndRefreshDatabase(application: Application, dao: MoviesDao, apiService: ApiService): Boolean {
    if (!isInternetAvailable(application)) {
        return false
    }

    try {
        val favouriteMovieIds = dao.getFavoriteMovieIds()
        dao.clearMovieCategoriesTable()
        dao.clearMoviesTable()

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
            dao.updateDatabase(allMovieCategories, allMoviesWithCategoryIds)
            favouriteMovieIds.forEach { dao.updateFavouriteStatus(it) }
            return true
        }
    } catch (e: Exception) {

    }
    return false
}

private fun isInternetAvailable(application: Application): Boolean {
    val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
    return networkCapabilities != null &&
            (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
}
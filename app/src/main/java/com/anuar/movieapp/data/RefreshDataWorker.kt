package com.anuar.movieapp.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.anuar.movieapp.data.database.AppDatabase
import com.anuar.movieapp.data.database.MovieCategoryDbModel
import com.anuar.movieapp.data.database.MovieDbModel
import com.anuar.movieapp.data.network.ApiFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit


class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val dao = AppDatabase.getInstance(context).moviesDao()
    private val apiService = ApiFactory.apiService

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
//            dao.clearMovieCategoriesTable()
//            dao.clearMoviesTable()
            val listIds = (200..210).toList()
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

                                val moviesWithCategoryId = movieCategories.movies.map { movie ->
                                    movie.copy(categoryId = movieCategories.category.id)
                                }
                                allMoviesWithCategoryIds.addAll(moviesWithCategoryId)
                            }
                        } catch (e: Exception) {

                        }
                    }
                }.awaitAll()
            }

            if (allMovieCategories.isNotEmpty() && allMoviesWithCategoryIds.isNotEmpty()) {
                dao.updateDatabase(allMovieCategories, allMoviesWithCategoryIds)
                Result.success()
            } else {
                Result.retry()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val NAME = "RefreshDataWorker"

        fun makeRequest(): PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<RefreshDataWorker>(2, TimeUnit.HOURS).build()
    }
}

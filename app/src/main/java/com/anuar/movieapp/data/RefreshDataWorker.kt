package com.anuar.movieapp.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.anuar.movieapp.data.database.AppDatabase
import com.anuar.movieapp.data.network.ApiFactory
import java.util.concurrent.TimeUnit


class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val dao = AppDatabase.getInstance(context).moviesDao()
    private val apiService = ApiFactory.apiService

    override suspend fun doWork(): Result {
        val listIds = (1..100).toList()
        listIds.forEach { listId ->
            try {
                val listOfMovies = apiService.moviesList(listId, 1)
                if (listOfMovies.items.isNotEmpty()) {
                    val movieCategories = listOfMovies.mapToDbModel()

                    dao.insertMovieCategory(movieCategories.category)

                    val moviesWithCategoryId = movieCategories.movies.map { movie ->
                        movie.copy(categoryId = movieCategories.category.id)
                    }
                    dao.insertMovies(moviesWithCategoryId)
                }

            } catch (e: Exception) {

            }
        }
        return Result.success()

    }

    companion object {

        const val NAME = "RefreshDataWorker"

        fun makeRequest(): PeriodicWorkRequest {
            return PeriodicWorkRequestBuilder<RefreshDataWorker>(10,TimeUnit.SECONDS).build()
        }
    }
}
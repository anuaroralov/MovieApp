package com.anuar.movieapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.anuar.movieapp.data.database.MovieCategoryDbModel
import com.anuar.movieapp.data.database.MovieCategoryWithMovies
import com.anuar.movieapp.data.database.MovieDbModel
import com.anuar.movieapp.data.database.MoviesDao
import com.anuar.movieapp.data.network.ApiFactory.apiService
import com.anuar.movieapp.data.worker.RefreshDataWorker
import com.anuar.movieapp.domain.MovieCategory
import com.anuar.movieapp.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dao: MoviesDao,
    private val application: Application
) : Repository {

    override fun getMovieCategoryList(): LiveData<List<MovieCategory>> {
        return dao.getMovieCategoriesWithMovies().map {
            it.map { mcwm: MovieCategoryWithMovies ->
                mcwm.category.toEntity().apply {
                    movies = mcwm.movies.map { it.toEntity() }
                }
            }
        }
    }

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniquePeriodicWork(
            RefreshDataWorker.NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            RefreshDataWorker.makeRequest()
        )
    }

    override suspend fun refreshData(): Boolean = withContext(Dispatchers.IO) {
        try {
//            dao.clearMovieCategoriesTable()
//            dao.clearMoviesTable()

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
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

}
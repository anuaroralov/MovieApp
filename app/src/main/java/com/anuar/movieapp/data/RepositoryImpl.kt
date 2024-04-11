package com.anuar.movieapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.anuar.movieapp.data.database.MovieCategoryWithMovies
import com.anuar.movieapp.data.database.MoviesDao
import com.anuar.movieapp.data.network.ApiFactory.apiService
import com.anuar.movieapp.data.worker.RefreshDataWorker
import com.anuar.movieapp.domain.Movie
import com.anuar.movieapp.domain.MovieCategory
import com.anuar.movieapp.domain.Repository
import kotlinx.coroutines.Dispatchers
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
        val workRequest = RefreshDataWorker.makeRequest()

        WorkManager.getInstance(application).enqueueUniquePeriodicWork(
            RefreshDataWorker.NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    override suspend fun refreshData(): Boolean = withContext(Dispatchers.IO) {
        loadDataAndRefreshDatabase(application,dao, apiService)
    }

    override fun getFavouriteMovieList(): LiveData<List<Movie>> {
        return dao.getFavoriteMovies().map { it.map { movie -> movie.toEntity() } }
    }

    override suspend fun updateFavouriteStatus(id: Int)=withContext(Dispatchers.IO) {
        dao.updateFavouriteStatus(id)
        }
}



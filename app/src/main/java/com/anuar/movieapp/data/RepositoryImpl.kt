package com.anuar.movieapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.anuar.movieapp.data.database.AppDatabase
import com.anuar.movieapp.data.database.MovieCategoryWithMovies
import com.anuar.movieapp.domain.MovieCategory
import com.anuar.movieapp.domain.Repository

class RepositoryImpl(private val application: Application) : Repository {

    private val dao = AppDatabase.getInstance(application).moviesDao()

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

}
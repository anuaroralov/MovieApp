package com.anuar.movieapp.data

import android.app.Application
import com.anuar.movieapp.data.network.ApiService
import com.anuar.movieapp.domain.MovieCategory
import com.anuar.movieapp.domain.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : Repository {

    override suspend fun getMovieCategoryList(): List<MovieCategory> {
        return loadData(apiService)
    }

}



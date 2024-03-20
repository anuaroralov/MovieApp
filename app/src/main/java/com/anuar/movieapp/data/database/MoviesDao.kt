package com.anuar.movieapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction


@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieCategory(category: MovieCategoryDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieDbModel>)

    @Transaction
    @Query("SELECT * FROM movie_categories")
    fun getMovieCategoriesWithMovies(): LiveData<List<MovieCategoryWithMovies>>

}

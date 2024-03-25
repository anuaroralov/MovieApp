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
    fun insertMovieCategories(categories: List<MovieCategoryDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieDbModel>)

    @Transaction
    fun updateDatabase(movieCategories: List<MovieCategoryDbModel>, movies: List<MovieDbModel>) {
        insertMovieCategories(movieCategories)
        insertMovies(movies)
    }

    @Query("DELETE FROM movie_categories")
    fun clearMovieCategoriesTable()

    @Query("DELETE FROM movies")
    fun clearMoviesTable()

    @Transaction
    @Query("SELECT * FROM movie_categories")
    fun getMovieCategoriesWithMovies(): LiveData<List<MovieCategoryWithMovies>>

    @Query("SELECT * FROM movies WHERE favourite = 1")
    fun getFavoriteMovies(): LiveData<List<MovieDbModel>>

    @Query("UPDATE movies SET favourite = CASE WHEN favourite = 0 THEN 1 ELSE 0 END WHERE id = :id")
    fun updateFavouriteStatus(id: Int)

    @Query("SELECT id FROM movies WHERE favourite = 1")
    fun getFavoriteMovieIds(): List<Int>

}

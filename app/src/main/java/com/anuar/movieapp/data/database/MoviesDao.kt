package com.anuar.movieapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction


@Dao
interface MoviesDao {

    // Вставка категории фильма
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieCategory(category: MovieCategoryDbModel)

    // Вставка списка фильмов
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieDbModel>)

    // Получение всех категорий фильмов с фильмами
    @Transaction
    @Query("SELECT * FROM movie_categories")
    suspend fun getMovieCategoriesWithMovies(): List<MovieCategoryWithMovies>

    // Получение конкретной категории по ID
    @Query("SELECT * FROM movie_categories WHERE id = :categoryId")
    suspend fun getMovieCategoryById(categoryId: Int): MovieCategoryDbModel?

}

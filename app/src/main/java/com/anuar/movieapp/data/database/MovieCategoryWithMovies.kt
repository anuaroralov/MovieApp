package com.anuar.movieapp.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class MovieCategoryWithMovies(
    @Embedded val category: MovieCategoryDbModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val movies: List<MovieDbModel>
)
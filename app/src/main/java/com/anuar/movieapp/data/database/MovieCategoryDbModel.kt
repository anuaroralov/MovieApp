package com.anuar.movieapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie_categories")
data class MovieCategoryDbModel(
    @PrimaryKey val id: Int,
    val categoryName: String,
) : Parcelable

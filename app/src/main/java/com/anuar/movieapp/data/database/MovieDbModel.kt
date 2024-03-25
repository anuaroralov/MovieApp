package com.anuar.movieapp.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "movies",
    foreignKeys = [
        ForeignKey(
            entity = MovieCategoryDbModel::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["categoryId"])]
)
@Parcelize
data class MovieDbModel(
    @PrimaryKey val id: Int,
    val categoryId: Int,
    val overview: String?,
    val posterPath: String?,
    val title: String?,
    val voteAverage: Double?,
    val favourite:Boolean
) : Parcelable

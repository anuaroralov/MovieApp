package com.anuar.movieapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val overview: String?,
    val posterPath: String?,
    val title: String?,
    val voteAverage: Double
) : Parcelable
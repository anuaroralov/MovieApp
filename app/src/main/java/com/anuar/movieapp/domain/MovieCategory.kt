package com.anuar.movieapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieCategory(
    val id: Int?,
    val categoryName: String?,
    val movies: List<Movie>?
) : Parcelable

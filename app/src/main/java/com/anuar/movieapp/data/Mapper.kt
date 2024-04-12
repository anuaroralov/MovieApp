package com.anuar.movieapp.data


import com.anuar.movieapp.data.network.model.ListOfMovies
import com.anuar.movieapp.data.network.model.MovieDto
import com.anuar.movieapp.domain.Movie
import com.anuar.movieapp.domain.MovieCategory

private const val BASE_URL = "https://image.tmdb.org/t/p/w500/"

internal fun MovieDto.mapToEntity(): Movie {
    return Movie(
        id = this.id ?: -1,
        overview = this.overview,
        posterPath = if (this.posterPath != null) BASE_URL + this.posterPath else null,
        title = this.title,
        voteAverage = this.voteAverage ?: 0.0,
        favourite = false
    )
}

internal fun ListOfMovies.toEntity(): MovieCategory {
    val movies = this.items.map { it.mapToEntity() }
    return MovieCategory(
        id = this.id ?: -1,
        categoryName = this.name,
        movies = movies
    )
}

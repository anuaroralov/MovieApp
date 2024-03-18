package com.anuar.movieapp.data

import com.anuar.movieapp.data.network.model.ListOfMovies
import com.anuar.movieapp.data.network.model.MovieDto
import com.anuar.movieapp.domain.Movie
import com.anuar.movieapp.domain.MovieCategory

private const val BASE_URL = "https://image.tmdb.org/t/p/w500/"
internal fun MovieDto.mapToEntity() = Movie(
    id = this.id?.toInt(),
    overview = this.overview,
    posterPath = BASE_URL + this.posterPath,
    title = this.title,
    voteAverage = String.format("%.2f", this.voteAverage)
)

internal fun ListOfMovies.mapToEntity() = MovieCategory(
    id = this.id?.toInt(),
    categoryName = this.name,
    movies = this.items.map { movieDto ->
        movieDto.mapToEntity()
    }
)


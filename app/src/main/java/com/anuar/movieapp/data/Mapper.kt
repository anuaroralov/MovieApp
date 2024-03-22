package com.anuar.movieapp.data

import com.anuar.movieapp.data.database.MovieCategoryDbModel
import com.anuar.movieapp.data.database.MovieCategoryWithMovies
import com.anuar.movieapp.data.database.MovieDbModel
import com.anuar.movieapp.data.network.model.ListOfMovies
import com.anuar.movieapp.data.network.model.MovieDto
import com.anuar.movieapp.domain.Movie
import com.anuar.movieapp.domain.MovieCategory

private const val BASE_URL = "https://image.tmdb.org/t/p/w500/"

internal fun MovieDto.mapToEntity() = Movie(
    id = this.id ?: -1,
    overview = this.overview,
    posterPath = BASE_URL + this.posterPath,
    title = this.title,
    voteAverage = Math.round(this.voteAverage?.times(100.0) ?: 0.0) / 100.0
)

internal fun ListOfMovies.mapToEntity() = MovieCategory(
    id = this.id ?: -1,
    categoryName = this.name,
    movies = this.items.map { movieDto ->
        movieDto.mapToEntity()
    },
)

internal fun MovieDto.mapToDbModel(categoryId: Int): MovieDbModel {
    return MovieDbModel(
        id = this.id ?: -1,
        categoryId = categoryId,
        title = this.title ?: "Нет названия",
        overview = this.overview ?: "Нет описания",
        posterPath = BASE_URL + (this.posterPath ?: "Нет картинки"),
        voteAverage = this.voteAverage ?: 0.0
    )
}

internal fun ListOfMovies.mapToDbModel(): MovieCategoryWithMovies {
    val categoryId = this.id ?: -1
    return MovieCategoryWithMovies(
        category = MovieCategoryDbModel(
            id = categoryId,
            categoryName = this.name ?: "Без названия"
        ),
        movies = this.items.map { movieDto ->
            movieDto.mapToDbModel(categoryId)
        }
    )
}

internal fun MovieDbModel.toEntity(): Movie = Movie(
    id = this.id,
    overview = this.overview,
    posterPath = this.posterPath,
    title = this.title,
    voteAverage = Math.round(this.voteAverage?.times(100.0) ?: 0.0) / 100.0
)

internal fun MovieCategoryDbModel.toEntity(): MovieCategory = MovieCategory(
    id = this.id,
    categoryName = this.categoryName,
    movies = mutableListOf() // Пустой список, фильмы добавятся позже
)





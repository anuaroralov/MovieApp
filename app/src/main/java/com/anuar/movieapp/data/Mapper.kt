package com.anuar.movieapp.data

import com.anuar.movieapp.data.network.model.MovieDto
import com.anuar.movieapp.domain.Movie

class Mapper {
    fun mapDtoModelToEntity(dtoModel: MovieDto) = Movie(
        id=dtoModel.id?.toInt(),
        overview=dtoModel.overview,
        posterPath=BASE_URL+dtoModel.posterPath,
        title=dtoModel.title,
        voteAverage=String.format("%.2f", dtoModel.voteAverage))

    companion object{
        private val BASE_URL="https://image.tmdb.org/t/p/w500/"
    }
}
package com.anuar.movieapp.domain

class GetMovieListUseCase(
    private val repository: Repository) {
    suspend operator fun invoke() = repository.getMovieList()
}
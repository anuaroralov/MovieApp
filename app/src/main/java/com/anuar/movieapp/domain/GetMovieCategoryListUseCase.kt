package com.anuar.movieapp.domain

class GetMovieCategoryListUseCase(private val repository: Repository) {
    suspend operator fun invoke() = repository.getMovieCategoryList()
}
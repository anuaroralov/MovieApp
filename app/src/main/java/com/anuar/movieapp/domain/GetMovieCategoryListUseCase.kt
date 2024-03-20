package com.anuar.movieapp.domain

class GetMovieCategoryListUseCase(private val repository: Repository) {
    operator fun invoke() = repository.getMovieCategoryList()
}
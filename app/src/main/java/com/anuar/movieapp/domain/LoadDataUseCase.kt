package com.anuar.movieapp.domain

class LoadDataUseCase(private val repository: Repository) {
    operator fun invoke() = repository.loadData()
}
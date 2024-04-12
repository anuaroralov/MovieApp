package com.anuar.movieapp.domain.useCase

import com.anuar.movieapp.domain.Repository
import javax.inject.Inject

class GetMovieCategoryListUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke() = repository.getMovieCategoryList()
}
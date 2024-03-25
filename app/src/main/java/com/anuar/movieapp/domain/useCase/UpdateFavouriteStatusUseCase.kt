package com.anuar.movieapp.domain.useCase

import com.anuar.movieapp.domain.Repository
import javax.inject.Inject

class UpdateFavouriteStatusUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id:Int) = repository.updateFavouriteStatus(id)
}
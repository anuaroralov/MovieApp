package com.anuar.movieapp.domain

import javax.inject.Inject

class RefreshDataUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke() = repository.refreshData()
}
package com.anuar.movieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anuar.movieapp.domain.useCase.GetFavouriteMovieListUseCase
import com.anuar.movieapp.domain.useCase.GetMovieCategoryListUseCase
import com.anuar.movieapp.domain.useCase.LoadDataUseCase
import com.anuar.movieapp.domain.useCase.RefreshDataUseCase
import com.anuar.movieapp.domain.useCase.UpdateFavouriteStatusUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyViewModel @Inject constructor(
    private val getMovieCategoryListUseCase: GetMovieCategoryListUseCase,
    private val loadDataUseCase: LoadDataUseCase,
    private val refreshDataUseCase: RefreshDataUseCase,
    private val getFavouriteMovieListUseCase: GetFavouriteMovieListUseCase,
    private val updateFavouriteStatusUseCase: UpdateFavouriteStatusUseCase,
    private val networkState: NetworkLiveData,
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val movieCategoriesList = getMovieCategoryListUseCase()

    val favouriteMoviesList = getFavouriteMovieListUseCase()

    init {
        loadDataUseCase()
    }

    fun refreshData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                refreshDataUseCase()
            } finally {
                _isLoading.value = false
            }
        }

    }

    fun updateFavouriteStatus(id: Int) {
        viewModelScope.launch { updateFavouriteStatusUseCase(id) }

    }

    override fun onCleared() {
        super.onCleared()
        networkState.removeObserver {}
    }
}
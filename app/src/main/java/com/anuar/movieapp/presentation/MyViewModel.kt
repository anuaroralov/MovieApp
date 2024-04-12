package com.anuar.movieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.anuar.movieapp.core.MovieCategoryPagingSource
import com.anuar.movieapp.data.network.ApiFactory.apiService
import com.anuar.movieapp.domain.MovieCategory
import com.anuar.movieapp.domain.useCase.GetMovieCategoryListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyViewModel @Inject constructor(
    private val getMovieCategoryListUseCase: GetMovieCategoryListUseCase,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _movieCategoryList = MutableLiveData<List<MovieCategory>>()
    val movieCategoryList: LiveData<List<MovieCategory>> = _movieCategoryList

    private val _errorMessages = MutableLiveData<String>()
    val errorMessages: LiveData<String> = _errorMessages

    init {
        refreshData()
    }

    fun refreshData() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                if (networkChecker.isInternetAvailable()) {
                    val movieCategories = getMovieCategoryListUseCase()
                    _movieCategoryList.postValue(movieCategories)
                } else {
                    postErrorMessage("Internet is not available")
                }
            } catch (e: Exception) {
                postErrorMessage(e.message ?: "An error occurred")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    private fun postErrorMessage(message: String) {
        _errorMessages.postValue(message)
    }

    fun clearErrorMessage() {
        _errorMessages.value = ""
    }

}


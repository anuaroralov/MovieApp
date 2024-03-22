package com.anuar.movieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anuar.movieapp.domain.GetMovieCategoryListUseCase
import com.anuar.movieapp.domain.LoadDataUseCase
import com.anuar.movieapp.domain.RefreshDataUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyViewModel @Inject constructor(
    private val getMovieCategoryListUseCase: GetMovieCategoryListUseCase,
    private val loadDataUseCase: LoadDataUseCase,
    private val refreshDataUseCase: RefreshDataUseCase,
    private val networkState: NetworkLiveData
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val movieCategoriesList = getMovieCategoryListUseCase()


    init {
        loadDataUseCase()
    }

    fun refreshData() {
        networkState.observeForever { isConnected ->
            if (isConnected) {
                viewModelScope.launch {
                    try {
                        _isLoading.value = true
                        refreshDataUseCase()
                    } finally {
                        _isLoading.value = false
                    }
                }
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        networkState.removeObserver {}
    }
}
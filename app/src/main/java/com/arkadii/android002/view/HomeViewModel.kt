package com.arkadii.android002.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arkadii.android002.api.ContentApiService
import com.arkadii.android002.domain.Content
import com.arkadii.android002.domain.ContentRepository
import com.arkadii.android002.domain.GetPopularTvListUseCase
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val contentRepository : ContentRepository = ContentApiService
    private val getPopularMoviesListUseCase = GetPopularTvListUseCase(contentRepository)
    private val getPopularTvListUseCase = GetPopularTvListUseCase(contentRepository)

    val errorMessage = MutableLiveData<String>()
    val popularMoviesList = MutableLiveData<List<Content>>()
    val popularTvList = MutableLiveData<List<Content>>()

    fun updatePopularMoviesList() {
        viewModelScope.launch {
            popularMoviesList.value = getPopularMoviesListUseCase.invoke()
        }
    }

    fun updatePopularTvList() {
        viewModelScope.launch {
            popularTvList.value = getPopularTvListUseCase.invoke()
        }
    }
}
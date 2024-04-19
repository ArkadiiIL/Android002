package com.arkadii.android002.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.arkadii.android002.api.ContentApiRepository
import com.arkadii.android002.domain.ContentRepository
import com.arkadii.android002.domain.GetPopularContentListUseCase

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val contentRepository: ContentRepository = ContentApiRepository
    private val getPopularMoviesListUseCase = GetPopularContentListUseCase(contentRepository)

    val popularList = getPopularMoviesListUseCase.invoke().cachedIn(viewModelScope)
}

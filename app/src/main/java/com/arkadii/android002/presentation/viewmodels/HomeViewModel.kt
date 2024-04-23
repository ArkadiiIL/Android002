package com.arkadii.android002.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.arkadii.android002.api.repositories.ContentApiRepository
import com.arkadii.android002.domain.repositories.ContentRepository
import com.arkadii.android002.domain.usecases.GetPopularContentListUseCase

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val contentRepository: ContentRepository = ContentApiRepository
    private val getPopularMoviesListUseCase = GetPopularContentListUseCase(contentRepository)
    val popularList = getPopularMoviesListUseCase.invoke()
}

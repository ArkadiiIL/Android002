package com.arkadii.android002.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.arkadii.android002.api.repositories.ContentApiRepository
import com.arkadii.android002.domain.repositories.ContentRepository
import com.arkadii.android002.domain.usecases.GetMoviesListByTitleUseCase
import com.arkadii.android002.domain.usecases.GetTvListByTitleUseCase

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val contentRepository: ContentRepository = ContentApiRepository
    private val getMoviesListByTitleUseCase = GetMoviesListByTitleUseCase(contentRepository)
    private val getTvListByTitleUseCase = GetTvListByTitleUseCase(contentRepository)

    fun searchMoviesByTitle(title: String) = getMoviesListByTitleUseCase.invoke(title)
    fun searchTvByTitle(title: String) = getTvListByTitleUseCase.invoke(title)
}
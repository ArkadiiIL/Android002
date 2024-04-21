package com.arkadii.android002.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.arkadii.android002.api.ContentApiRepository
import com.arkadii.android002.domain.ContentRepository
import com.arkadii.android002.domain.GetMoviesListByTitleUseCase
import com.arkadii.android002.domain.GetTvListByTitleUseCase

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val contentRepository: ContentRepository = ContentApiRepository
    private val getMoviesListByTitleUseCase = GetMoviesListByTitleUseCase(contentRepository)
    private val getTvListByTitleUseCase = GetTvListByTitleUseCase(contentRepository)

    fun searchMoviesByTitle(title: String) = getMoviesListByTitleUseCase.invoke(title)
    fun searchTvByTitle(title: String) = getTvListByTitleUseCase.invoke(title)
}
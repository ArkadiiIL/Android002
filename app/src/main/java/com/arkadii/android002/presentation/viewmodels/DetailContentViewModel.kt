package com.arkadii.android002.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arkadii.android002.api.repositories.ContentApiRepository
import com.arkadii.android002.domain.data.ContentDetail
import com.arkadii.android002.domain.repositories.ContentRepository
import com.arkadii.android002.domain.usecases.GetContentDetailUseCase
import com.arkadii.android002.presentation.sessionmanager.SessionManager
import kotlinx.coroutines.launch

class DetailContentViewModel(application: Application): AndroidViewModel(application) {
    private val sessionManager = SessionManager(application)
    private val contentRepository: ContentRepository = ContentApiRepository
    private val getContentDetailUseCase = GetContentDetailUseCase(contentRepository)
    private val _updateContentDetail = MutableLiveData<ContentDetail>()
    val updateContentDetail: LiveData<ContentDetail> = _updateContentDetail

    fun isSessionActive() = sessionManager.isSessionActive()

    fun getDetailContent(contentId: Long, isMovie: Boolean) {
        viewModelScope.launch {
            val contentDetail = getContentDetailUseCase.invoke(contentId, isMovie)
            _updateContentDetail.value = contentDetail
        } }
    }
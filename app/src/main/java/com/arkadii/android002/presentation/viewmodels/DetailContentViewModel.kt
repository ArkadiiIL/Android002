package com.arkadii.android002.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arkadii.android002.api.errors.FavoriteError
import com.arkadii.android002.api.repositories.ContentApiRepository
import com.arkadii.android002.api.repositories.FavoriteApiRepository
import com.arkadii.android002.domain.data.ContentDetail
import com.arkadii.android002.domain.repositories.ContentRepository
import com.arkadii.android002.domain.repositories.FavoriteRepository
import com.arkadii.android002.domain.usecases.AddToFavoriteUseCase
import com.arkadii.android002.domain.usecases.GetContentDetailUseCase
import com.arkadii.android002.domain.usecases.IsFavoriteUseCase
import com.arkadii.android002.domain.usecases.RemoveFromFavoriteUseCase
import com.arkadii.android002.presentation.sessionmanager.SessionManager
import com.arkadii.android002.utils.NetworkUtils
import kotlinx.coroutines.launch

class DetailContentViewModel(private val application: Application) : AndroidViewModel(application) {
    private val sessionManager = SessionManager(application)
    private val contentRepository: ContentRepository = ContentApiRepository
    private val favoriteRepository: FavoriteRepository = FavoriteApiRepository
    private val getContentDetailUseCase = GetContentDetailUseCase(contentRepository)
    private val addToFavoriteUseCase = AddToFavoriteUseCase(favoriteRepository)
    private val removeFromFavoriteUseCase = RemoveFromFavoriteUseCase(favoriteRepository)
    private val isFavoriteUseCase = IsFavoriteUseCase(favoriteRepository)
    private val _updateContentDetail = MutableLiveData<ContentDetail>()
    val updateContentDetail: LiveData<ContentDetail> = _updateContentDetail
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite
    private val _addedToFavorite = MutableLiveData<Boolean>()
    val addedToFavorite: LiveData<Boolean> = _addedToFavorite
    private val _removedFromFavorite = MutableLiveData<Boolean>()
    val removedFromFavorite: LiveData<Boolean> = _removedFromFavorite
    private val _getFavoriteError = MutableLiveData<Unit>()
    val getFavoriteError: LiveData<Unit> = _getFavoriteError


    fun isSessionActive() = sessionManager.isSessionActive()

    fun getDetailContent(contentId: Long, isMovie: Boolean) {
        viewModelScope.launch {
            val contentDetail = getContentDetailUseCase.invoke(contentId, isMovie)
            _updateContentDetail.value = contentDetail
        }
    }

    fun addToFavorite(isMovie: Boolean, mediaId: Long) {
        try {
            if (!NetworkUtils.isNetworkAvailable(application))
                throw FavoriteError.RequestFavoriteError
            if (sessionManager.isSessionActive()) {
                viewModelScope.launch {
                    val success = addToFavoriteUseCase.invoke(
                        accountId = sessionManager.user.id,
                        sessionId = sessionManager.sessionId!!,
                        isMovie = isMovie,
                        mediaId = mediaId
                    )
                    _addedToFavorite.value = success
                }
            }
        } catch (e: FavoriteError) {
            _getFavoriteError.value = Unit
        }
    }

    fun removeFromFavorite(isMovie: Boolean, mediaId: Long) {
        try {
            if (!NetworkUtils.isNetworkAvailable(application))
                throw FavoriteError.RequestFavoriteError
            if (sessionManager.isSessionActive()) {
                viewModelScope.launch {
                    val success = removeFromFavoriteUseCase.invoke(
                        accountId = sessionManager.user.id,
                        sessionId = sessionManager.sessionId!!,
                        isMovie = isMovie,
                        mediaId = mediaId
                    )
                    _removedFromFavorite.value = success
                }
            }
        } catch (e: FavoriteError) {
            _getFavoriteError.value = Unit
        }
    }

    fun isFavorite(isMovie: Boolean, mediaId: Long) {
        try {
            if (!NetworkUtils.isNetworkAvailable(application))
                throw FavoriteError.RequestFavoriteError
            if (sessionManager.isSessionActive()) {
                viewModelScope.launch {
                    val isFavorite = isFavoriteUseCase.invoke(
                        sessionManager.sessionId!!,
                        isMovie,
                        mediaId
                    )
                    _isFavorite.value = isFavorite
                }
            }
        } catch (e: FavoriteError) {
            _getFavoriteError.value = Unit
        }
    }
}
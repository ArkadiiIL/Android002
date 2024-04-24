package com.arkadii.android002.domain.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.arkadii.android002.domain.data.Content
import com.arkadii.android002.domain.data.ContentDetail

interface ContentRepository {
    fun getPopularContentList(): LiveData<PagingData<Content>>
    fun getMoviesListByTitle(title: String): LiveData<PagingData<Content>>
    fun getTvListByTitle(title: String): LiveData<PagingData<Content>>
    fun getFavoriteMoviesList(accountId: Int, sessionId: String): LiveData<PagingData<Content>>
    fun getFavoriteTvList(accountId: Int, sessionId: String): LiveData<PagingData<Content>>
    suspend fun getContentDetail(content_id: Long, isMovie: Boolean): ContentDetail


}
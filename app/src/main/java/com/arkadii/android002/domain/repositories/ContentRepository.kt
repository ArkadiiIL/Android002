package com.arkadii.android002.domain.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.arkadii.android002.domain.data.Content

interface ContentRepository {
    fun getPopularContentList(): LiveData<PagingData<Content>>
    fun getMoviesListByTitle(title: String): LiveData<PagingData<Content>>
    fun getTvListByTitle(title: String): LiveData<PagingData<Content>>


}
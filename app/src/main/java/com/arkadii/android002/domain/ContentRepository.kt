package com.arkadii.android002.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData

interface ContentRepository {
    fun getPopularContentList(): LiveData<PagingData<Content>>
    fun getMoviesListByTitle(title: String): LiveData<PagingData<Content>>
    fun getTvListByTitle(title: String): LiveData<PagingData<Content>>


}
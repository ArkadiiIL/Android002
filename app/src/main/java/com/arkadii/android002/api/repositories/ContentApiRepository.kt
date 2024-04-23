package com.arkadii.android002.api.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.arkadii.android002.api.pagingsources.PopularContentPagingSource
import com.arkadii.android002.api.pagingsources.SearchMoviesPagingSource
import com.arkadii.android002.api.pagingsources.SearchTVPagingSource
import com.arkadii.android002.api.serivces.ContentService
import com.arkadii.android002.domain.repositories.ContentRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ContentApiRepository : ContentRepository {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val PAGE_SIZE = 18
    private const val PREFETCH_DISTANCE = 180

    private val contentService: ContentService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ContentService::class.java)
    }

    override fun getPopularContentList() = Pager(
        config = PagingConfig(PAGE_SIZE, PREFETCH_DISTANCE),
        pagingSourceFactory = { PopularContentPagingSource(contentService) }
    ).liveData

    override fun getMoviesListByTitle(title: String) = Pager(
        config = PagingConfig(PAGE_SIZE, PREFETCH_DISTANCE),
        pagingSourceFactory = { SearchMoviesPagingSource(contentService, title) }
    ).liveData

    override fun getTvListByTitle(title: String) = Pager(
        config = PagingConfig(PAGE_SIZE, PREFETCH_DISTANCE),
        pagingSourceFactory = { SearchTVPagingSource(contentService, title) }
    ).liveData
}
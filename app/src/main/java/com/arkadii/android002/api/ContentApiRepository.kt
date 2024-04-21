package com.arkadii.android002.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.arkadii.android002.domain.ContentRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ContentApiRepository : ContentRepository {
    private val contentService: ContentService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ContentService::class.java)
    }

    override fun getPopularContentList() = Pager(
        config = PagingConfig(18, 180),
        pagingSourceFactory = { PopularContentPagingSource(contentService) }
    ).liveData

    override fun getMoviesListByTitle(title: String) = Pager(
        config = PagingConfig(18, 180),
        pagingSourceFactory = { SearchMoviesPagingSource(contentService, title) }
    ).liveData

    override fun getTvListByTitle(title: String) = Pager(
        config = PagingConfig(18, 180),
        pagingSourceFactory = { SearchTVPagingSource(contentService, title) }
    ).liveData

    private const val BASE_URL = "https://api.themoviedb.org/3/"
}
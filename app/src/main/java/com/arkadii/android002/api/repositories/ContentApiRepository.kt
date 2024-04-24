package com.arkadii.android002.api.repositories

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.arkadii.android002.BuildConfig
import com.arkadii.android002.api.errors.DetailError
import com.arkadii.android002.api.mappers.ContentMapper
import com.arkadii.android002.api.pagingsources.FavoriteMoviePagingSource
import com.arkadii.android002.api.pagingsources.FavoriteTvPagingSource
import com.arkadii.android002.api.pagingsources.PopularContentPagingSource
import com.arkadii.android002.api.pagingsources.SearchMoviesPagingSource
import com.arkadii.android002.api.pagingsources.SearchTVPagingSource
import com.arkadii.android002.api.serivces.ContentService
import com.arkadii.android002.domain.data.Content
import com.arkadii.android002.domain.data.ContentDetail
import com.arkadii.android002.domain.repositories.ContentRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ContentApiRepository : ContentRepository {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = BuildConfig.API_KEY
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

    override fun getFavoriteMoviesList(
        accountId: Int,
        sessionId: String
    ): LiveData<PagingData<Content>> = Pager(
        config = PagingConfig(PAGE_SIZE, PREFETCH_DISTANCE),
        pagingSourceFactory = { FavoriteMoviePagingSource(contentService, accountId, sessionId) }
    ).liveData

    override fun getFavoriteTvList(
        accountId: Int,
        sessionId: String
    ): LiveData<PagingData<Content>> = Pager(
        config = PagingConfig(PAGE_SIZE, PREFETCH_DISTANCE),
        pagingSourceFactory = { FavoriteTvPagingSource(contentService, accountId, sessionId) }
    ).liveData

    override suspend fun getContentDetail(content_id: Long, isMovie: Boolean): ContentDetail {
        return if (isMovie) {
            getMovieContentDetail(content_id)
        } else getTvContentDetail(content_id)
    }

    private suspend fun getMovieContentDetail(content_id: Long): ContentDetail {
        val response = contentService.getMovieDetail(content_id, API_KEY)
        if (response.isSuccessful && response.body() != null) {
            val movieDetail = response.body()!!
            val castResponse = contentService.getMovieCast(content_id, API_KEY)
            if (castResponse.isSuccessful && castResponse.body() != null) {
                val movieCast = castResponse.body()!!
                return ContentMapper.mapMovieDetailDtoAndMovieCastDtoToContentDetail(
                    movieDetail,
                    movieCast
                )
            } else throw DetailError.RequestCastError
        } else throw DetailError.RequestDetailError
    }

    private suspend fun getTvContentDetail(content_id: Long): ContentDetail {
        val response = contentService.getTvDetail(content_id, API_KEY)
        if (response.isSuccessful && response.body() != null) {
            val tvDetail = response.body()!!
            val castResponse = contentService.getTvCast(content_id, API_KEY)
            if (castResponse.isSuccessful && castResponse.body() != null) {
                val tvCast = castResponse.body()!!
                return ContentMapper.mapTvDetailDtoAndTvCastDtoToContentDetail(
                    tvDetail,
                    tvCast
                )
            } else throw DetailError.RequestCastError
        } else throw DetailError.RequestDetailError
    }

}
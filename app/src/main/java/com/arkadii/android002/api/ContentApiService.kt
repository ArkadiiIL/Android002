package com.arkadii.android002.api

import com.arkadii.android002.domain.Content
import com.arkadii.android002.domain.ContentRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ContentApiService : ContentRepository {
    private val contentService: ContentService by lazy {
       val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ContentService::class.java)
    }

    override suspend fun getPopularMoviesList(): List<Content> {
        return contentService.getMoviePopularList(API_KEY)
            .map { DtoToDomainMapper.mapMovieDtoToContent(it) }
    }

    override suspend fun getPopularTvList(): List<Content> {
        return contentService.getTvPopularList(API_KEY)
            .map { DtoToDomainMapper.mapTvDtoToContent(it) }
    }

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "02b113b496621e5a49428c55c55a3ccc"
}
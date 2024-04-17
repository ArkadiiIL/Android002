package com.arkadii.android002.domain

interface ContentRepository {
    suspend fun getPopularMoviesList(): List<Content>
    suspend fun getPopularTvList(): List<Content>
}
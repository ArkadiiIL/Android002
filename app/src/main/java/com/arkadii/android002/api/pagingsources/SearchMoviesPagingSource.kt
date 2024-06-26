package com.arkadii.android002.api.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arkadii.android002.BuildConfig
import com.arkadii.android002.api.mappers.ContentMapper
import com.arkadii.android002.api.serivces.ContentService
import com.arkadii.android002.domain.data.Content

class SearchMoviesPagingSource(
    private val service: ContentService,
    private val title: String,
) : PagingSource<Int, Content>() {
    override fun getRefreshKey(state: PagingState<Int, Content>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
        return try {
            val position = params.key ?: 1
            val response = service.searchMovies(API_KEY, position, title)

            if (response.isSuccessful && response.body() != null) {
                val list = response.body()!!.results.map(ContentMapper::mapMovieDtoToContent)
                LoadResult.Page(
                    data = list,
                    prevKey = if (position == 1) null else (position - 1),
                    nextKey = if (
                        position < response.body()!!.totalPages.toLong()
                    ) (position + 1) else null
                )
            } else {
                LoadResult.Error(throw Exception("No Response"))
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }
}
package com.arkadii.android002.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arkadii.android002.domain.Content

class ContentPagingSource(private val service: ContentService) : PagingSource<Int, Content>() {
    override fun getRefreshKey(state: PagingState<Int, Content>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
        return try {
            val position = params.key ?: 1
            val responseMovie = service.getMoviePopularList(API_KEY, position)
            val responseTv = service.getTvPopularList(API_KEY, position)

            if (
                responseMovie.isSuccessful
                && responseTv.isSuccessful
                && responseMovie.body() != null
                && responseTv.body() != null
            ) {
                val moviesContendList = responseMovie.body()!!.results
                    .map(DtoToDomainMapper::mapMovieDtoToContent)
                val tvContentList = responseTv.body()!!.results
                    .map(DtoToDomainMapper::mapTvDtoToContent)
                val data = (moviesContendList + tvContentList)
                    .sortedByDescending { content -> content.popularity }

                LoadResult.Page(
                    data = data,
                    prevKey = if (position == 1) null else (position - 1),
                    nextKey = if (
                        position == responseMovie.body()!!.totalPages
                        || position == responseTv.body()!!.totalPages
                    ) null else (position + 1)
                )
            } else {
                LoadResult.Error(throw Exception("No Response"))
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val API_KEY = "02b113b496621e5a49428c55c55a3ccc"
    }
}
package com.arkadii.android002.api.mappers

import com.arkadii.android002.api.dto.CastDto
import com.arkadii.android002.api.dto.CastResponseDto
import com.arkadii.android002.api.dto.GenreDto
import com.arkadii.android002.api.dto.MovieDetailDto
import com.arkadii.android002.api.dto.MovieDto
import com.arkadii.android002.api.dto.TvDetailDto
import com.arkadii.android002.api.dto.TvDto
import com.arkadii.android002.domain.data.Content
import com.arkadii.android002.domain.data.ContentDetail

object ContentMapper {
    fun mapMovieDtoToContent(movie: MovieDto) =
        Content(
            movie.id,
            movie.name,
            movie.posterPath,
            movie.popularity,
            true
        )

    fun mapTvDtoToContent(tv: TvDto) =
        Content(
            tv.id,
            tv.name,
            tv.posterPath,
            tv.popularity,
            false
        )

    fun mapMovieDetailDtoAndMovieCastDtoToContentDetail(
        movieDetailDto: MovieDetailDto,
        movieCastDto: CastResponseDto
    ) = ContentDetail(
        name = movieDetailDto.name,
        releaseYear = extractYearFromDate(movieDetailDto.releaseDate),
        runtime = movieDetailDto.runtime.toString(),
        numberOfEpisodes = null,
        posterPath = movieDetailDto.posterPath,
        overview = movieDetailDto.overview,
        genres = convertGenresListToString(movieDetailDto.genres),
        cast = convertCastListToString(movieCastDto.castList),
        isMovie = true
    )

    fun mapTvDetailDtoAndTvCastDtoToContentDetail(
        tvDetailDto: TvDetailDto,
        tvCastDto: CastResponseDto
    ) = ContentDetail(
        name = tvDetailDto.name,
        releaseYear = extractYearFromDate(tvDetailDto.releaseDate),
        runtime = null,
        numberOfEpisodes = tvDetailDto.numberOfEpisodes.toString(),
        posterPath = tvDetailDto.posterPath,
        overview = tvDetailDto.overview,
        genres = convertGenresListToString(tvDetailDto.genres),
        cast = convertCastListToString(tvCastDto.castList),
        isMovie = false
    )

    private fun convertGenresListToString(list: List<GenreDto>): String {
        return if (list.isEmpty()) {
            ""
        } else {
            list.joinToString(separator = ", ") { it.genreName }
        }
    }

    private fun convertCastListToString(list: List<CastDto>): String {
        return if (list.isEmpty()) {
            ""
        } else {
            list.joinToString(separator = ", ") { it.name }
        }
    }

    private fun extractYearFromDate(dateString: String): String {
        val parts = dateString.split("-")
        return parts.firstOrNull() ?: ""
    }
}
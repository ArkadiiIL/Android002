package com.arkadii.android002.api.mappers

import com.arkadii.android002.api.dto.MovieDto
import com.arkadii.android002.api.dto.TvDto
import com.arkadii.android002.domain.data.Content

object ContentMapper {
    fun mapMovieDtoToContent(movie: MovieDto) =
        Content(
            movie.id,
            movie.name,
            movie.posterPath,
            movie.popularity
        )

    fun mapTvDtoToContent(tv: TvDto) =
        Content(
            tv.id,
            tv.name,
            tv.posterPath,
            tv.popularity
        )

}
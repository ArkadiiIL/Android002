package com.arkadii.android002.api

import com.arkadii.android002.domain.Content

object DtoToDomainMapper {
    fun mapMovieDtoToContent(movie: MovieDto) = Content(movie.id, movie.name, movie.posterPath)

    fun mapTvDtoToContent(tv: TvDto) = Content(tv.id, tv.name, tv.posterPath)

}
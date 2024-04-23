package com.arkadii.android002.api.mappers

import com.arkadii.android002.api.dto.SessionResponseDto
import com.arkadii.android002.domain.data.Session

object SessionMapper {
    fun mapSessionResponseDtoToSession(sessionResponseDto: SessionResponseDto): Session {
        return Session(
            success = sessionResponseDto.success,
            sessionId = sessionResponseDto.sessionId
        )
    }
}

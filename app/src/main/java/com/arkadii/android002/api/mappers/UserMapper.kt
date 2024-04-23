package com.arkadii.android002.api.mappers

import com.arkadii.android002.api.dto.AccountDetailsDto
import com.arkadii.android002.domain.data.User

object UserMapper {
    fun mapAccountDetailsDtoToUser(detailsDto: AccountDetailsDto) = User(
        id = detailsDto.id,
        name = detailsDto.name,
        userName = detailsDto.username,
        includeAdult = detailsDto.includeAdult,
        avatarPath = detailsDto.avatar.avatarPath.avatarPath
    )
}
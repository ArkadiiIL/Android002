package com.arkadii.android002.domain.usecases

import com.arkadii.android002.domain.repositories.AuthenticationRepository

class GetRequestTokenUseCase(private val authenticationRepository: AuthenticationRepository) {
    suspend operator fun invoke() = authenticationRepository.getRequestToken()
}
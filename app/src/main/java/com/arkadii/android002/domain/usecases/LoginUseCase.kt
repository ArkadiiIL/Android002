package com.arkadii.android002.domain.usecases

import com.arkadii.android002.domain.repositories.AuthenticationRepository

class LoginUseCase(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(requestToken: String) =
        repository.login(requestToken)
}
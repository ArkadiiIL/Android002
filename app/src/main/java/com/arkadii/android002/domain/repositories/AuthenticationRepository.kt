package com.arkadii.android002.domain.repositories

import com.arkadii.android002.domain.data.Session
import com.arkadii.android002.domain.data.User

interface AuthenticationRepository {
    suspend fun getRequestToken(): String
    suspend fun login(requestToken: String): Session
    suspend fun getUser(sessionId: String): User
}
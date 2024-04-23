package com.arkadii.android002.api.repositories

import com.arkadii.android002.api.errors.AuthError
import com.arkadii.android002.api.mappers.SessionMapper
import com.arkadii.android002.api.mappers.UserMapper
import com.arkadii.android002.api.serivces.LoginService
import com.arkadii.android002.domain.repositories.AuthenticationRepository
import com.arkadii.android002.domain.data.Session
import com.arkadii.android002.domain.data.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthenticationApiRepository : AuthenticationRepository {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "02b113b496621e5a49428c55c55a3ccc"
    private val loginService: LoginService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(LoginService::class.java)
    }

    override suspend fun getRequestToken(): String {
        val response = loginService.getRequestToken(API_KEY)
        if (response.isSuccessful && response.body() != null) {
            val token = response.body()!!
            if (token.requestToken != null) {
                return token.requestToken
            } else throw AuthError.RequestTokenFailure
        } else throw AuthError.RequestTokenFailure
    }

    override suspend fun login(requestToken: String): Session {
        val authenticationResponse = loginService.validateTokenWithLogin(API_KEY, requestToken)
        if (authenticationResponse.isSuccessful && authenticationResponse.body() != null) {
            val token = authenticationResponse.body()!!
            return SessionMapper.mapSessionResponseDtoToSession(token)
        } else throw AuthError.RequestTokenFailure
    }

    override suspend fun getUser(sessionId: String): User {
        val response = loginService.getAccountDetails(API_KEY, sessionId)
        if (response.isSuccessful && response.body() != null) {
            return UserMapper.mapAccountDetailsDtoToUser(response.body()!!)
        } else {
            throw AuthError.RequestUserDetailsFailure
        }
    }
}
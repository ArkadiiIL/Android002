package com.arkadii.android002.api.serivces

import com.arkadii.android002.api.dto.AccountDetailsDto
import com.arkadii.android002.api.dto.SessionResponseDto
import com.arkadii.android002.api.dto.TokenResponseDto
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @GET("authentication/token/new")
    suspend fun getRequestToken(@Query("api_key") apiKey: String): Response<TokenResponseDto>

    @FormUrlEncoded
    @POST("authentication/session/new")
    suspend fun validateTokenWithLogin(
        @Query("api_key") apiKey: String,
        @Field("request_token") requestToken: String
    ): Response<SessionResponseDto>

    @GET("account")
    suspend fun getAccountDetails(
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): Response<AccountDetailsDto>
}
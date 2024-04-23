package com.arkadii.android002.api.errors

sealed class AuthError(message: String) : Throwable(message) {
    object RequestTokenFailure : AuthError("Failed to obtain request token")
    object ValidateTokenFailure : AuthError("Failed to validate token with login")
    object RequestTokenDataFailure : AuthError("Failed to obtain valid request token data")
    object RequestUserDetailsFailure : AuthError("Failed to obtain user details")
}
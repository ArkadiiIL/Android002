package com.arkadii.android002.api.errors

sealed class FavoriteError(message: String) : Throwable(message) {
    object RequestFavoriteError : FavoriteError("Failed to obtain favorite response")
}

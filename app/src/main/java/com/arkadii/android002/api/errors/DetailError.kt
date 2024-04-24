package com.arkadii.android002.api.errors

sealed class DetailError(message: String) : Throwable(message) {
    object RequestDetailError : DetailError("Failed to obtain detail data")
    object RequestCastError : DetailError("Failed to obtain cast data")
}

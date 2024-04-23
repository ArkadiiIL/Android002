package com.arkadii.android002.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.arkadii.android002.domain.data.User
import com.google.gson.Gson

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences
    private val gson = Gson()

    init {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        sharedPreferences = EncryptedSharedPreferences.create(
            context,
            "session_preferences",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    var sessionId: String?
        get() = sharedPreferences.getString(KEY_SESSION_ID, null)
        set(value) = sharedPreferences.edit().putString(KEY_SESSION_ID, value).apply()
    var user: User
        get() {
            val json = sharedPreferences.getString(KEY_USER, null)
            return gson.fromJson(json, User::class.java)
        }
        set(value) {
            value.let {
                sharedPreferences.edit().putString(KEY_USER, gson.toJson(it)).apply()
            }

        }

    fun clearSession() {
        sharedPreferences.edit().clear().apply()
    }

    fun isSessionActive() = sessionId != null

    companion object {
        private const val KEY_SESSION_ID = "session_id"
        private const val KEY_USER = "user"
    }
}
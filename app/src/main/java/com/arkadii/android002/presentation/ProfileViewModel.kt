package com.arkadii.android002.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.arkadii.android002.api.errors.AuthError
import com.arkadii.android002.api.repositories.AuthenticationApiRepository
import com.arkadii.android002.domain.repositories.AuthenticationRepository
import com.arkadii.android002.domain.usecases.GetRequestTokenUseCase
import com.arkadii.android002.domain.usecases.GetUserUseCase
import com.arkadii.android002.domain.usecases.LoginUseCase
import com.arkadii.android002.domain.data.User
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val authenticationRepository: AuthenticationRepository = AuthenticationApiRepository
    private val loginUseCase = LoginUseCase(authenticationRepository)
    private val getUserUseCase = GetUserUseCase(authenticationRepository)
    private val getRequestTokenUseCase = GetRequestTokenUseCase(authenticationRepository)
    private val sessionManager = SessionManager(application)
    private val _updateRequestToken = MutableLiveData<String>()
    private val _updateUser = MutableLiveData<User>()
    private val _authError = MutableLiveData<AuthError>()
    val updateRequestToken: LiveData<String> = _updateRequestToken
    val updateUser: LiveData<User> = _updateUser
    val authError: LiveData<AuthError> = _authError

    fun getRequestToken() {
        viewModelScope.launch {
            val requestToken = getRequestTokenUseCase.invoke()
            _updateRequestToken.value = requestToken
        }
    }

    fun login(requestToken: String) {
        viewModelScope.launch {
            try {
                val session = loginUseCase.invoke(requestToken)
                if (session.success && session.sessionId != null) {
                    val user = getUserUseCase.invoke(session.sessionId)
                    sessionManager.user = user
                    sessionManager.sessionId = session.sessionId
                    _updateUser.value = user
                }
            } catch (error: AuthError) {
                _authError.value = error
            }
        }
    }

    fun isSessionActive() = sessionManager.isSessionActive()

    fun logOut() = sessionManager.clearSession()

    fun getUser() {
        _updateUser.value = sessionManager.user
    }
}
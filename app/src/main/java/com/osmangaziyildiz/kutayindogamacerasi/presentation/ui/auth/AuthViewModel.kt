package com.osmangaziyildiz.kutayindogamacerasi.presentation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osmangaziyildiz.kutayindogamacerasi.domain.repository.AuthRepository
import com.osmangaziyildiz.kutayindogamacerasi.domain.usecase.auth.LoginUseCase
import com.osmangaziyildiz.kutayindogamacerasi.domain.usecase.auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState

    fun login(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Kullanıcı adı veya şifre boş olamaz")
            return
        }
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            loginUseCase(username, password).onSuccess {
                _authState.value = AuthState.Authenticated
            }.onFailure { exception ->
                _authState.value = AuthState.Error(
                    exception.message ?: "Bir şeyler yanlış gitti"
                )
            }
        }
    }

    fun signup(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Kullanıcı adı veya şifre boş olamaz")
            return
        }
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            signUpUseCase(username, password).onSuccess {
                _authState.value = AuthState.Registered
            }.onFailure { exception ->
                _authState.value = AuthState.Error(
                    exception.message ?: "Bir şeyler yanlış gitti"
                )
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Unauthenticated
    }

    fun signout() {
        _authState.value = AuthState.Unauthenticated
    }
}

sealed class AuthState { data object Authenticated : AuthState()
    data object Unauthenticated : AuthState()
    data object Registered : AuthState()
    data object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}
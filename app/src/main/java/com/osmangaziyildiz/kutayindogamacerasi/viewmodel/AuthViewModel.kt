package com.osmangaziyildiz.kutayindogamacerasi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osmangaziyildiz.kutayindogamacerasi.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
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
            val result = authRepository.loginUser(username, password)
            _authState.value = if (result.isSuccess) {
                AuthState.Authenticated
            } else {
                AuthState.Error(result.exceptionOrNull()?.message ?: "Bir şeyler yanlış gitti")
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
            val result = authRepository.registerUser(username, password)
            _authState.value = if (result.isSuccess) {
                AuthState.Registered
            } else {
                AuthState.Error(result.exceptionOrNull()?.message ?: "Bir şeyler yanlış gitti")
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
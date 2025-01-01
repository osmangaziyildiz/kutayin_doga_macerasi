package com.osmangaziyildiz.kutayindogamacerasi.domain.usecase.auth

import com.osmangaziyildiz.kutayindogamacerasi.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): Result<String> =
        repository.loginUser(username, password)
}
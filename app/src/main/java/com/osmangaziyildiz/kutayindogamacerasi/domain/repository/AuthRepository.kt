package com.osmangaziyildiz.kutayindogamacerasi.domain.repository

interface AuthRepository {
    suspend fun loginUser(username: String, password: String): Result<String>
    suspend fun signUpUser(username: String, password: String): Result<String>
}
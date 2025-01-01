package com.osmangaziyildiz.kutayindogamacerasi.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.osmangaziyildiz.kutayindogamacerasi.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun loginUser(username: String, password: String): Result<String> =
        try {
            val userDoc = firestore.collection("users").document(username).get().await()

            if (userDoc.exists() && userDoc.getString("password") == password) {
                Result.success("Giriş başarılı")
            } else {
                Result.failure(Exception("Kullanıcı adı veya şifre hatalı"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }


    override suspend fun signUpUser(username: String, password: String): Result<String> =
        try {
            val userDoc = firestore.collection("users").document(username).get().await()
            if (userDoc.exists()) {
                Result.failure(Exception("Kullanıcı adı zaten kullanılıyor"))
            } else {
                val userData = hashMapOf(
                    "username" to username,
                    "password" to password
                )
                firestore.collection("users").document(username).set(userData).await()
                Result.success("Kayıt başarılı")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

}
package com.osmangaziyildiz.kutayindogamacerasi.repositories

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
) {

    suspend fun registerUser(username: String, password: String): Result<String> {
        return try {
            val userDoc = firestore.collection("users").document(username).get().await()
            if (userDoc.exists()) {
                Result.failure(Exception("Kullanıcı adı zaten kullanılıyor"))
            } else {
                val userData = hashMapOf(
                    "username" to username, "password" to password
                )
                firestore.collection("users").document(username).set(userData).await()
                Result.success("Kayıt başarılı")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    suspend fun loginUser(username: String, password: String): Result<String> {
        return try {
            val userDoc = firestore.collection("users").document(username).get().await()
            if (userDoc.exists() && userDoc.getString("password") == password) {
                Result.success("Giriş başarılı")
            } else {
                Result.failure(Exception("Kullanıcı adı veya şifre yanlış"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

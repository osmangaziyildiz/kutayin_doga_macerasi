package com.osmangaziyildiz.kutayindogamacerasi.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.osmangaziyildiz.kutayindogamacerasi.models.CategoryModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
) {

    suspend fun fetchCategories(): Result<List<CategoryModel>> {
        return try {
            val snapshot = firestore.collection("categories").get().await()
            val categories = snapshot.documents.map { doc ->
                CategoryModel(
                    id = doc.id,
                    name = doc.getString("name") ?: "",
                    description = doc.getString("description") ?: "",
                    icon = doc.getString("icon") ?: ""
                )
            }
            Result.success(categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

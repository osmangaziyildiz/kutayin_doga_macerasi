package com.osmangaziyildiz.kutayindogamacerasi.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.osmangaziyildiz.kutayindogamacerasi.data.mapper.toDomain
import com.osmangaziyildiz.kutayindogamacerasi.data.remote.dto.CategoryDto
import com.osmangaziyildiz.kutayindogamacerasi.domain.model.Category
import com.osmangaziyildiz.kutayindogamacerasi.domain.repository.CategoryRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : CategoryRepository {

    override suspend fun getCategories(): Result<List<Category>> =
        try {
            val snapshot = firestore.collection("categories").get().await()
            val categories = snapshot.documents.map { doc ->
                CategoryDto(
                    id = doc.id,
                    name = doc.getString("name") ?: "",
                    description = doc.getString("description") ?: "",
                    icon = doc.getString("icon") ?: ""
                ).toDomain()
            }
            Result.success(categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
}
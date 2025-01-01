package com.osmangaziyildiz.kutayindogamacerasi.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.osmangaziyildiz.kutayindogamacerasi.data.mapper.toDomain
import com.osmangaziyildiz.kutayindogamacerasi.data.remote.dto.LearningContentDto
import com.osmangaziyildiz.kutayindogamacerasi.domain.model.LearningContent
import com.osmangaziyildiz.kutayindogamacerasi.domain.repository.LearningRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LearningRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : LearningRepository {

    override suspend fun getLearningContents(categoryId: String): Result<List<LearningContent>> =
        try {
            val snapshot =
                firestore.collection("learning_contents")
                    .whereEqualTo("category_id", categoryId)
                    .orderBy("order")
                    .get().await()

            val contents = snapshot.documents.map { doc ->
                LearningContentDto(
                    categoryId = doc.getString("category_id") ?: "",
                    description = doc.getString("description") ?: "",
                    imagePath = doc.getString("image_path") ?: "",
                    audioPath = doc.getString("audio_path") ?: "",
                    order = doc.getLong("order")?.toInt() ?: 0
                ).toDomain()
            }
            Result.success(contents)
        } catch (e: Exception) {
            Result.failure(e)
        }
}
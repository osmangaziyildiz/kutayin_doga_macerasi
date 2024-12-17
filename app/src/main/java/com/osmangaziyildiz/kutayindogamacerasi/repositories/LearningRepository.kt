package com.osmangaziyildiz.kutayindogamacerasi.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.osmangaziyildiz.kutayindogamacerasi.models.LearningContentModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LearningRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
) {

    suspend fun fetchLearningContents(categoryId: String): Result<List<LearningContentModel>> {
        return try {
            val snapshot =
                firestore.collection("learning_contents").whereEqualTo("category_id", categoryId)
                    .orderBy("order").get().await()

            val contents = snapshot.documents.map { doc ->
                LearningContentModel(
                    categoryId = doc.getString("category_id") ?: "",
                    description = doc.getString("description") ?: "",
                    imagePath = doc.getString("image_path") ?: "",
                    audioPath = doc.getString("audio_path") ?: "",
                    order = doc.getLong("order")?.toInt() ?: 0,
                )
            }
            Result.success(contents)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

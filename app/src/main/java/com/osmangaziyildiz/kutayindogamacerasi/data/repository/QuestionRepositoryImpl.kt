package com.osmangaziyildiz.kutayindogamacerasi.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.osmangaziyildiz.kutayindogamacerasi.data.mapper.toDomain
import com.osmangaziyildiz.kutayindogamacerasi.data.remote.dto.QuestionDto
import com.osmangaziyildiz.kutayindogamacerasi.domain.model.Question
import com.osmangaziyildiz.kutayindogamacerasi.domain.repository.QuestionRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : QuestionRepository {

    override suspend fun getQuestions(categoryId: String): Result<List<Question>> =
        try {
            val snapshot = firestore.collection("questions")
                .whereEqualTo("category_id", categoryId)
                .get()
                .await()

            val questions = snapshot.documents.map { doc ->
                QuestionDto(
                    categoryId = doc.getString("category_id") ?: "",
                    correctOption = doc.getString("correct_option") ?: "",
                    options = (doc.get("options") as? List<String>) ?: emptyList(),
                    questionTip = doc.getString("question_tip") ?: ""
                ).toDomain()
            }
            Result.success(questions)
        } catch (e: Exception) {
            Result.failure(e)
        }
}
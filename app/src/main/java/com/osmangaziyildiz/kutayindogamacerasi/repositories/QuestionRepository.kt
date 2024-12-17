package com.osmangaziyildiz.kutayindogamacerasi.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.osmangaziyildiz.kutayindogamacerasi.models.QuestionModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QuestionRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
) {
    suspend fun fetchQuestions(categoryId: String): Result<List<QuestionModel>> {
        return try {
            val snapshot = firestore.collection("questions")
                .whereEqualTo("category_id", categoryId)
                .get()
                .await()

            val questions = snapshot.documents.map { doc ->
                QuestionModel(
                    categoryId = doc.getString("category_id")!!,
                    correctOption = doc.getString("correct_option")!!,
                    options = (doc.get("options") as? List<String>)!!,
                    questionTip = doc.getString("question_tip")!!,
                )
            }
            Result.success(questions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
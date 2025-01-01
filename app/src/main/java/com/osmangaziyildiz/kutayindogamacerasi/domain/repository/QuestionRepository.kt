package com.osmangaziyildiz.kutayindogamacerasi.domain.repository

import com.osmangaziyildiz.kutayindogamacerasi.domain.model.Question

interface QuestionRepository {
    suspend fun getQuestions(categoryId: String): Result<List<Question>>
}

package com.osmangaziyildiz.kutayindogamacerasi.domain.usecase.question

import com.osmangaziyildiz.kutayindogamacerasi.domain.model.Question
import com.osmangaziyildiz.kutayindogamacerasi.domain.repository.QuestionRepository
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val repository: QuestionRepository
) {
    suspend operator fun invoke(categoryId: String): Result<List<Question>> =
        repository.getQuestions(categoryId)
}
package com.osmangaziyildiz.kutayindogamacerasi.domain.usecase.learning

import com.osmangaziyildiz.kutayindogamacerasi.domain.model.LearningContent
import com.osmangaziyildiz.kutayindogamacerasi.domain.repository.LearningRepository
import javax.inject.Inject

class GetLearningContentsUseCase @Inject constructor(
    private val repository: LearningRepository
) {
    suspend operator fun invoke(categoryId: String): Result<List<LearningContent>> =
        repository.getLearningContents(categoryId)
}
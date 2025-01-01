package com.osmangaziyildiz.kutayindogamacerasi.domain.repository

import com.osmangaziyildiz.kutayindogamacerasi.domain.model.LearningContent

interface LearningRepository {
    suspend fun getLearningContents(categoryId: String) : Result<List<LearningContent>>
}

package com.osmangaziyildiz.kutayindogamacerasi.domain.repository

import com.osmangaziyildiz.kutayindogamacerasi.domain.model.Category

interface CategoryRepository {
    suspend fun getCategories(): Result<List<Category>>
}

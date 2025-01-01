package com.osmangaziyildiz.kutayindogamacerasi.domain.usecase.category

import com.osmangaziyildiz.kutayindogamacerasi.domain.model.Category
import com.osmangaziyildiz.kutayindogamacerasi.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): Result<List<Category>> = repository.getCategories()
}
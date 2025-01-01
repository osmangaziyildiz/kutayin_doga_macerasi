package com.osmangaziyildiz.kutayindogamacerasi.domain.model

data class LearningContent(
    val categoryId: String,
    val description: String,
    val imagePath: String,
    val audioPath: String,
    val order: Int
)

package com.osmangaziyildiz.kutayindogamacerasi.domain.model

data class Question(
    val categoryId: String,
    val correctOption: String,
    val options: List<String>,
    val questionTip: String
)

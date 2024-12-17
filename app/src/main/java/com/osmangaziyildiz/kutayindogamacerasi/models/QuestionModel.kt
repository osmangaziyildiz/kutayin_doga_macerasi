package com.osmangaziyildiz.kutayindogamacerasi.models

data class QuestionModel(
    val categoryId: String,
    val correctOption: String,
    val options: List<String>,
    val questionTip: String
)

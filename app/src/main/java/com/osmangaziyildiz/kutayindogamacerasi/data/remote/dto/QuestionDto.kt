package com.osmangaziyildiz.kutayindogamacerasi.data.remote.dto

data class QuestionDto(
    val categoryId: String = "",
    val correctOption: String = "",
    val options: List<String> = emptyList(),
    val questionTip: String = ""
)

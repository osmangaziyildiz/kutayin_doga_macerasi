package com.osmangaziyildiz.kutayindogamacerasi.data.mapper

import com.osmangaziyildiz.kutayindogamacerasi.data.remote.dto.QuestionDto
import com.osmangaziyildiz.kutayindogamacerasi.domain.model.Question

fun QuestionDto.toDomain() = Question(
    categoryId = categoryId,
    correctOption = correctOption,
    options = options,
    questionTip = questionTip
)
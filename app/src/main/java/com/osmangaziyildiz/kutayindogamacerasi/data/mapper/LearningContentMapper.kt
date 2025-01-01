package com.osmangaziyildiz.kutayindogamacerasi.data.mapper

import com.osmangaziyildiz.kutayindogamacerasi.data.remote.dto.LearningContentDto
import com.osmangaziyildiz.kutayindogamacerasi.domain.model.LearningContent

fun LearningContentDto.toDomain() = LearningContent(
    categoryId = categoryId,
    description = description,
    imagePath = imagePath,
    audioPath = audioPath,
    order = order
)
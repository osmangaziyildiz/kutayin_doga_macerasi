package com.osmangaziyildiz.kutayindogamacerasi.data.mapper

import com.osmangaziyildiz.kutayindogamacerasi.data.remote.dto.CategoryDto
import com.osmangaziyildiz.kutayindogamacerasi.domain.model.Category


fun CategoryDto.toDomain() = Category(
    id = id,
    name = name,
    description = description,
    icon = icon
)
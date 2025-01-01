package com.osmangaziyildiz.kutayindogamacerasi.data.mapper

import com.osmangaziyildiz.kutayindogamacerasi.data.remote.dto.UserDto
import com.osmangaziyildiz.kutayindogamacerasi.domain.model.User

fun UserDto.toDomain() = User(
    username = username,
    password = password
)

fun User.toDto() = UserDto(
    username = username,
    password = password
)
package com.wahabahmad.mula.request

import com.wahabahmad.mula.model.User

data class CreateGameRequest(
    val user: User,
    val difficulty: List<String>,
    val grade: Int,
    val subject: String,
    val topics: List<String>
)
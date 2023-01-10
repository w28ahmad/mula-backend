package com.wahabahmad.mula.request

data class CreateGameRequest(
    val difficulty: List<String>,
    val grade: Int,
    val subject: String,
    val topics: List<String>
)
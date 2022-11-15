package com.wahabahmad.mula.request

data class QuestionSolutionRequest (
    val userId: String,
    val questionId: Int,
    var solution: String,
)

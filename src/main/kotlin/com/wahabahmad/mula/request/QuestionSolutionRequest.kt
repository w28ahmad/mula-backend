package com.wahabahmad.mula.request

import com.wahabahmad.mula.model.User

data class QuestionSolutionRequest(
    val sessionId: String,
    val user: User,
    val questionId: Int,
    var solution: String,
)

package com.wahabahmad.mula.request

import com.wahabahmad.mula.data.User

data class QuestionSolutionRequest(
    val sessionId: String,
    val user: User,
    val questionId: Int,
    var solution: String,
)

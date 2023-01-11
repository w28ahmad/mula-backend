package com.wahabahmad.mula.request

import com.wahabahmad.mula.model.User

data class CreateQuestionSolutionRequest(
    val roomId: String,
    val user: User,
    val questionId: Int,
    val solution: String
)

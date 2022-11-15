package com.wahabahmad.mula.response

import com.wahabahmad.mula.data.SocketMessageTypes

data class QuestionSolutionResponse(
    val type: SocketMessageTypes = SocketMessageTypes.USER_SOLUTION,
    val userId: String,
    val isCorrect: Boolean,
)

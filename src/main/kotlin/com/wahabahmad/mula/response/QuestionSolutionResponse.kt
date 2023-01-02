package com.wahabahmad.mula.response

import com.wahabahmad.mula.data.SocketMessageTypes
import com.wahabahmad.mula.model.Question
import com.wahabahmad.mula.model.User

data class QuestionSolutionResponse(
    val type: SocketMessageTypes = SocketMessageTypes.SCORE_RESPONSE,
    val user: User,
    val backupQuestion: Question? = null,
)

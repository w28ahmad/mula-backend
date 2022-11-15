package com.wahabahmad.mula.response

import com.wahabahmad.mula.data.SocketMessageTypes
import com.wahabahmad.mula.model.Question

data class QuestionSetResponse (
    val type: SocketMessageTypes = SocketMessageTypes.QUESTION_SET,
    val questions: List<Question>
)
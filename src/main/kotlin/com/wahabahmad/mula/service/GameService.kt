package com.wahabahmad.mula.service

import com.wahabahmad.mula.data.SocketMessageTypes
import com.wahabahmad.mula.data.User
import com.wahabahmad.mula.repository.QuestionSolutionsRepository
import com.wahabahmad.mula.response.QuestionSolutionResponse
import com.wahabahmad.mula.util.SessionUtil
import org.springframework.stereotype.Service

@Service
class GameService(
    private val questionSolutionsRepository: QuestionSolutionsRepository,
    private val sessionService: SessionService,
    private val sessionUtil: SessionUtil,
) {
    companion object {
        const val QUESTION_SET_SIZE = 5
    }

    fun checkQuestionSolution(
        sessionId: String,
        user: User,
        questionId: Int,
        solution: String
    ): QuestionSolutionResponse {
        val actualSolution = questionSolutionsRepository.findByQuestionId(questionId)
        val isCorrect = actualSolution.correctSolution == solution
        val updatedUser = if (isCorrect) sessionUtil.incrementPlayerScore(sessionId, user) else user
        /* Game finished */
        if (updatedUser.score == QUESTION_SET_SIZE) sessionService.disconnect(sessionId, listOf(updatedUser))
        return QuestionSolutionResponse(
            SocketMessageTypes.SCORE_RESPONSE,
            updatedUser
        )
    }
}
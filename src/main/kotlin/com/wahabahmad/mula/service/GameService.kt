package com.wahabahmad.mula.service

import com.wahabahmad.mula.data.SocketMessageTypes
import com.wahabahmad.mula.repository.QuestionRepository
import com.wahabahmad.mula.repository.QuestionSolutionsRepository
import com.wahabahmad.mula.request.QuestionSolutionRequest
import com.wahabahmad.mula.response.QuestionSetResponse
import com.wahabahmad.mula.response.QuestionSolutionResponse
import org.springframework.stereotype.Service

@Service
class GameService(
    private val questionRepository: QuestionRepository,
    private val questionSolutionsRepository: QuestionSolutionsRepository
) {
    fun getQuestion(): QuestionSetResponse {
        return QuestionSetResponse(
            SocketMessageTypes.QUESTION_SET,
            mutableListOf(questionRepository.findById(1).get())
        )
    }

    fun checkQuestionSolution(questionSolution : QuestionSolutionRequest): QuestionSolutionResponse {
        val solution = questionSolutionsRepository.findByQuestionId(questionSolution.questionId)
        return QuestionSolutionResponse(
            SocketMessageTypes.USER_SOLUTION,
            questionSolution.userId,
            solution.correctSolution == questionSolution.solution
        )
    }
}
package com.wahabahmad.mula.service

import com.wahabahmad.mula.data.SocketMessageTypes
import com.wahabahmad.mula.repository.QuestionRepository
import com.wahabahmad.mula.repository.QuestionSolutionsRepository
import com.wahabahmad.mula.request.QuestionSolutionRequest
import com.wahabahmad.mula.response.QuestionSetResponse
import com.wahabahmad.mula.response.QuestionSolutionResponse
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class GameService(
    private val questionRepository: QuestionRepository,
    private val questionSolutionsRepository: QuestionSolutionsRepository
) {

    private val QUESTION_SET_SIZE = 2

    fun getQuestions(): QuestionSetResponse {
        val questionCount : Int = questionRepository.count().toInt()
        val randomSample = generateSequence {
            Random.nextInt(1, questionCount+1)
        }
            .distinct()
            .take(QUESTION_SET_SIZE)
            .toSet()

        return QuestionSetResponse(
            SocketMessageTypes.QUESTION_SET,
            questionRepository.findByIdIn(randomSample.toList())
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
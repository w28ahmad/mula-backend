package com.wahabahmad.mula.service

import com.wahabahmad.mula.data.SocketMessageTypes
import com.wahabahmad.mula.repository.QuestionRepository
import com.wahabahmad.mula.repository.QuestionSolutionsRepository
import com.wahabahmad.mula.request.QuestionSolutionRequest
import com.wahabahmad.mula.response.QuestionSetResponse
import com.wahabahmad.mula.response.QuestionSolutionResponse
import com.wahabahmad.mula.util.SessionUtil
import org.springframework.stereotype.Service

@Service
class GameService(
    private val questionRepository: QuestionRepository,
    private val questionSolutionsRepository: QuestionSolutionsRepository,
    private val sessionUtil: SessionUtil,
    private val diagramService: DiagramService
) {
    companion object {
        const val QUESTION_SET_SIZE = 5
    }

    fun getQuestions(sessionId: String): QuestionSetResponse =
        with(sessionUtil) {
            QuestionSetResponse(
                SocketMessageTypes.QUESTION_SET,
                questionRepository.findByIdIn(
                    getSessionQuestions(sessionId).toList()
                ).map { question ->
                    question.diagram = question.getDiagramUrl(diagramService)
                    question
                }
            )
        }

    fun getQuestionsByIdx(questionId: Int): QuestionSetResponse =
        QuestionSetResponse(
            SocketMessageTypes.QUESTION_SET,
            listOf(questionRepository.findById(questionId).get()).map { question ->
                question.diagram = question.getDiagramUrl(diagramService)
                println(question.toString())
                question
            }
        )

    fun checkQuestionSolution(questionSolution : QuestionSolutionRequest): QuestionSolutionResponse {
        val solution = questionSolutionsRepository.findByQuestionId(questionSolution.questionId)
        return QuestionSolutionResponse(
            SocketMessageTypes.USER_SOLUTION,
            questionSolution.userId,
            solution.correctSolution == questionSolution.solution
        )
    }
}
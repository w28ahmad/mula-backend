package com.wahabahmad.mula.service

import com.wahabahmad.mula.model.Question
import com.wahabahmad.mula.repository.QuestionRepository
import com.wahabahmad.mula.request.QuestionSolutionRequest
import com.wahabahmad.mula.response.QuestionSolutionResponse
import org.springframework.stereotype.Service

@Service
class GameService(
    private val questionRepository: QuestionRepository
) {
    fun getQuestion(): Question {
        return questionRepository.findById(1).get()
    }

    fun checkQuestionSolution(questionSolution : QuestionSolutionRequest): QuestionSolutionResponse =
        TODO("Check solution response")
}
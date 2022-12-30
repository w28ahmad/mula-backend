package com.wahabahmad.mula.controller

import com.wahabahmad.mula.request.BeginGameDebugRequest
import com.wahabahmad.mula.request.BeginGameRequest
import com.wahabahmad.mula.request.QuestionSolutionRequest
import com.wahabahmad.mula.response.QuestionSetResponse
import com.wahabahmad.mula.response.QuestionSolutionResponse
import com.wahabahmad.mula.service.GameService
import com.wahabahmad.mula.service.QuestionService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController(
    private val questionService: QuestionService,
    private val gameService: GameService
) {

    @MessageMapping("/game")
    @SendTo("/topic/game")
    fun beginGame(beginGameRequest: BeginGameRequest): QuestionSetResponse =
        questionService.getQuestions(beginGameRequest.sessionId)

    @MessageMapping("/gameDebug")
    @SendTo("/topic/game")
    fun debugGame(beginGameRequest: BeginGameDebugRequest): QuestionSetResponse =
        questionService.getQuestionsByIdx(beginGameRequest.questionId)

    @MessageMapping("/solution")
    @SendTo("/topic/game")
    fun checkQuestion(questionSolution: QuestionSolutionRequest): QuestionSolutionResponse =
        with(questionSolution) {
            gameService.checkQuestionSolution(sessionId, user, questionId, solution)
        }
}
package com.wahabahmad.mula.controller

import com.wahabahmad.mula.request.BeginGameDebugRequest
import com.wahabahmad.mula.request.BeginGameRequest
import com.wahabahmad.mula.request.QuestionSolutionRequest
import com.wahabahmad.mula.response.QuestionSetResponse
import com.wahabahmad.mula.response.QuestionSolutionResponse
import com.wahabahmad.mula.service.GameService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController(
    private val gameService: GameService
) {

    @MessageMapping("/game")
    @SendTo("/topic/game")
    fun beginGame(beginGameRequest: BeginGameRequest): QuestionSetResponse =
        gameService.getQuestions(beginGameRequest.sessionId)

    @MessageMapping("/gameDebug")
    @SendTo("/topic/game")
    fun debugGame(beginGameRequest: BeginGameDebugRequest): QuestionSetResponse =
        gameService.getQuestionsByIdx(beginGameRequest.questionIdx)

    @MessageMapping("/solution")
    @SendTo("/topic/game")
    fun checkQuestion(questionSolution: QuestionSolutionRequest) : QuestionSolutionResponse =
        gameService.checkQuestionSolution(questionSolution)
}
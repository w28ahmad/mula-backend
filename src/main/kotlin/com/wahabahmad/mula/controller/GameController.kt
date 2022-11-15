package com.wahabahmad.mula.controller

import com.wahabahmad.mula.request.QuestionSolutionRequest
import com.wahabahmad.mula.response.QuestionSetResponse
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
    fun beginGame(): QuestionSetResponse = gameService.getQuestion() // TODO: get a set of questions

    @MessageMapping("/question")
    @SendTo("/topic/game")
    fun checkQuestion(questionSolution: QuestionSolutionRequest) = gameService.checkQuestionSolution(questionSolution)
}
package com.wahabahmad.mula.controller

import com.wahabahmad.mula.model.Question
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
    fun beginGame(): Question = gameService.getQuestion()
}
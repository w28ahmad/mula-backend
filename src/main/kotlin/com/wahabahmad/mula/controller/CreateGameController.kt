package com.wahabahmad.mula.controller

import com.wahabahmad.mula.data.SocketMessageTypes
import com.wahabahmad.mula.request.BeginCreateGameRequest
import com.wahabahmad.mula.request.CreateGameRequest
import com.wahabahmad.mula.request.CreateQuestionSolutionRequest
import com.wahabahmad.mula.request.createGameDisconnectionRequest
import com.wahabahmad.mula.response.CreateGameResponse
import com.wahabahmad.mula.response.QuestionSolutionResponse
import com.wahabahmad.mula.response.createGameDisconnectionResponse
import com.wahabahmad.mula.service.CreateGameService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.RestController

@RestController
class CreateGameController(
    private val createGameService: CreateGameService,
    private val messageTemplate: SimpMessagingTemplate
) {
    @MessageMapping("/createGame/connect")
    @SendTo("/topic/connect")
    fun createGame(createGameRequest: CreateGameRequest): CreateGameResponse =
        with(createGameRequest) {
            createGameService.createGameConnect(
                user,
                difficulty,
                grade,
                subject,
                topics
            )
        }


    @MessageMapping("/createGame/disconnect")
    @SendTo("/topic/disconnect")
    fun disconnect(request: createGameDisconnectionRequest): createGameDisconnectionResponse =
        createGameService.createGameDisconnect(request.roomId, request.users)


    @MessageMapping("/createGame/solution")
    fun checkQuestion(questionSolution: CreateQuestionSolutionRequest) =
        with(questionSolution) {
            val (updatedUser, backupQuestion) = createGameService.checkQuestionSolution(
                roomId,
                user,
                questionId,
                solution
            )
            messageTemplate.convertAndSend(
                "/topic/$roomId/game",
                QuestionSolutionResponse(
                    SocketMessageTypes.SCORE_RESPONSE,
                    updatedUser,
                    backupQuestion
                )
            )
        }

    @MessageMapping("/createGame/getQuestions")
    fun getQuestions(beginCreateGameRequest: BeginCreateGameRequest) =
        with(beginCreateGameRequest) {
            messageTemplate.convertAndSend(
                "/topic/$roomId/game",
                createGameService.getQuestions(roomId)
            )
        }

}
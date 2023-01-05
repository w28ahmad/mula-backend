package com.wahabahmad.mula.controller

import com.wahabahmad.mula.data.SocketMessageTypes
import com.wahabahmad.mula.request.BeginGameDebugRequest
import com.wahabahmad.mula.request.BeginGameRequest
import com.wahabahmad.mula.request.QuestionSolutionRequest
import com.wahabahmad.mula.response.QuestionSolutionResponse
import com.wahabahmad.mula.service.GameService
import com.wahabahmad.mula.service.QuestionService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController(
    private val questionService: QuestionService,
    private val gameService: GameService,
    private val messagingTemplate: SimpMessagingTemplate
) {

    companion object {
        const val DEBUG_SESSION_ID = "5e34"
    }

    @MessageMapping("/game")
    fun beginGame(beginGameRequest: BeginGameRequest) =
        with(beginGameRequest) {
            messagingTemplate.convertAndSend(
                "/topic/$sessionId/game",
                questionService.getQuestions(sessionId)
            )
        }

    @MessageMapping("/gameDebug")
    fun debugGame(beginGameRequest: BeginGameDebugRequest) {
        messagingTemplate.convertAndSend(
            "/topic/$DEBUG_SESSION_ID/game",
            questionService.getQuestionsByIdx(beginGameRequest.questionId)
        )
    }

    @MessageMapping("/solution")
    fun checkQuestion(questionSolution: QuestionSolutionRequest) =
        with(questionSolution) {
            val (updatedUser, backupQuestion) = gameService.checkQuestionSolution(
                sessionId,
                user,
                questionId,
                solution
            )
            messagingTemplate.convertAndSend(
                "/topic/$sessionId/game",
                QuestionSolutionResponse(
                    SocketMessageTypes.SCORE_RESPONSE,
                    updatedUser,
                    backupQuestion
                )
            )
        }
}
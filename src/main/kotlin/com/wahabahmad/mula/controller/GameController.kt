package com.wahabahmad.mula.controller

import com.wahabahmad.mula.data.SocketMessageTypes
import com.wahabahmad.mula.request.BeginGameDebugRequest
import com.wahabahmad.mula.request.BeginGameRequest
import com.wahabahmad.mula.request.QuestionSolutionRequest
import com.wahabahmad.mula.response.BackupQuestionResponse
import com.wahabahmad.mula.response.QuestionSetResponse
import com.wahabahmad.mula.response.QuestionSolutionResponse
import com.wahabahmad.mula.service.GameService
import com.wahabahmad.mula.service.QuestionService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController(
    private val questionService: QuestionService,
    private val gameService: GameService,
    private val messagingTemplate: SimpMessagingTemplate
) {

    @MessageMapping("/game")
    fun beginGame(beginGameRequest: BeginGameRequest) =
        with(beginGameRequest) {
            messagingTemplate.convertAndSend(
                "/topic/${sessionId}/game",
                questionService.getQuestions(sessionId)
            )
        }

    @MessageMapping("/gameDebug")
    @SendTo("/topic/game")
    fun debugGame(beginGameRequest: BeginGameDebugRequest): QuestionSetResponse =
        questionService.getQuestionsByIdx(beginGameRequest.questionId)

    @MessageMapping("/solution")
    @SendTo("/topic/game")
    fun checkQuestion(questionSolution: QuestionSolutionRequest) =
        with(questionSolution) {
            val (updatedUser, isCorrect) = gameService.checkQuestionSolution(sessionId, user, questionId, solution)
            messagingTemplate.convertAndSend(
                "/topic/$sessionId/game",
                QuestionSolutionResponse(
                    SocketMessageTypes.SCORE_RESPONSE,
                    updatedUser
                )
            )

            if (!isCorrect)
                messagingTemplate.convertAndSend(
                    "/topic/${user.id}/game",
                    BackupQuestionResponse(
                        SocketMessageTypes.BACKUP_QUESTION,
                        // TODO: we need to ensure the backup question is unique from the existing question set
                        gameService.backupQuestion()
                    )
                )
        }
}
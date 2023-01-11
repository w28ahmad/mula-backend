package com.wahabahmad.mula.service

import com.wahabahmad.mula.model.Question
import com.wahabahmad.mula.model.User
import com.wahabahmad.mula.repository.QuestionSolutionsRepository
import com.wahabahmad.mula.response.CreateGameResponse
import com.wahabahmad.mula.response.createGameDisconnectionResponse
import com.wahabahmad.mula.service.GameService.Companion.QUESTION_SET_SIZE
import com.wahabahmad.mula.util.RoomUtil
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreateGameService(
    private val roomUtil: RoomUtil,
    private val questionSolutionsRepository: QuestionSolutionsRepository,
    private val diagramService: DiagramService
) {


    fun createGameConnect(
        user: User,
        difficulty: List<String>,
        grade: Int,
        subject: String,
        topics: List<String>
    ): CreateGameResponse =
        with(roomUtil) {
            user.id = UUID.randomUUID().toString()
            val roomId = roomUtil.createRoom(difficulty, grade, subject, topics)
            addPlayerToRoom(roomId, user)
            return CreateGameResponse(
                roomId = roomId,
                users = getRoomPlayers(roomId)
            )
        }


    fun createGameDisconnect(roomId: String, users: List<User>) =
        with(roomUtil) {
            users.forEach { user ->
                if (removePlayerFromRoom(roomId, user) == 0) {
                    deleteRoom(roomId)
                }
            }
            createGameDisconnectionResponse(
                roomId = roomId,
                users = getRoomPlayers(roomId)
            )
        }

    fun checkQuestionSolution(
        roomId: String,
        user: User,
        questionId: Int,
        solution: String,
    ): Pair<User, List<Question>> {
        val roomBackupSize = roomUtil.getRoomBackupSize(roomId)
        val actualSolution = questionSolutionsRepository.findByQuestionId(questionId)
        val isCorrect = solution == actualSolution.correctSolution

        val updatedUser =
            if (isCorrect) roomUtil.incrementPlayerScore(roomId, user)
            else roomUtil.decrementPlayerScore(roomId, user)

        val backupQuestion: List<Question> =
            if (roomBackupSize < updatedUser.numberIncorrect) {
                roomUtil.incrementRoomBackupSize(roomId)
                roomUtil.getBackupQuestion(roomId).map { question ->
                    question.diagram = question.getDiagramUrl(diagramService)
                    question
                }
            } else emptyList()

        /* Game Finished */
        if (updatedUser.score == QUESTION_SET_SIZE)
            createGameDisconnect(roomId, listOf(updatedUser))
        return Pair(updatedUser, backupQuestion)
    }
}
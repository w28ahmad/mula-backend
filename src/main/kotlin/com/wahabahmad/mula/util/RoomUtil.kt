package com.wahabahmad.mula.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wahabahmad.mula.exception.CommonExceptions
import com.wahabahmad.mula.model.Question
import com.wahabahmad.mula.model.User
import com.wahabahmad.mula.repository.QuestionDetailsRepository
import com.wahabahmad.mula.repository.QuestionRepository
import com.wahabahmad.mula.request.CreateGameRequest
import com.wahabahmad.mula.service.GameService.Companion.QUESTION_SET_SIZE
import org.springframework.stereotype.Service
import redis.clients.jedis.Jedis
import java.util.*

@Service
class RoomUtil(
    private val questionRepository: QuestionRepository,
    private val questionDetailsRepository: QuestionDetailsRepository,
    private val randomUtil: RandomUtil,
    private val jedis: Jedis,
) {

    private val mapper = jacksonObjectMapper()

    companion object {
        const val ROOM_DETAILS = "room:details"
        const val ROOM_PLAYERS = "room:players"
        const val ROOM_PLAYER_COUNT = "room:playerCount"
        const val ROOM_QUESTIONS = "room:questions"

        const val ROOM_BACKUP_QUESTIONS_SIZE = "room:backupQuestionSize"
        const val MAX_TTL: Long = 6 * 60 * 60 // 6hrs
    }

    fun createRoom(createGameRequest: CreateGameRequest): String =
        with(UUID.randomUUID().toString()) {
            jedis.setex("$ROOM_PLAYER_COUNT:$this", MAX_TTL, "0")
            jedis.setex(
                "$ROOM_DETAILS:$this",
                MAX_TTL,
                mapper.writeValueAsString(createGameRequest)
            )
            jedis.setex(
                "$ROOM_QUESTIONS:$this", MAX_TTL,
                mapper.writeValueAsString(
                    randomUtil.randomNumbersInSet(
                        questionDetailsRepository.getFilteredQuestionIds(
                            createGameRequest.subject,
                            createGameRequest.grade,
                            createGameRequest.difficulty,
                            createGameRequest.topics
                        ),
                        QUESTION_SET_SIZE
                    )
                )
            )
            this
        }

    fun getRoomDetails(roomId: String): CreateGameRequest =
        mapper.readValue(
            jedis.get("$ROOM_DETAILS:$roomId"),
            CreateGameRequest::class.java
        )

    fun addPlayerToRoom(roomId: String, user: User): String =
        with(roomId) {
            jedis.incr("${ROOM_PLAYER_COUNT}:$this")
            jedis.lpush(
                "${ROOM_PLAYERS}:${this}",
                mapper.writeValueAsString(user)
            )
            jedis.expire("${ROOM_PLAYERS}:$this", MAX_TTL)
            this
        }


    fun getRoomPlayers(roomId: String): List<User> =
        mutableListOf<User>().apply {
            jedis.lrange("${ROOM_PLAYERS}:$roomId", 0, -1)
                .forEach { user ->
                    add(
                        mapper.readValue(user, User::class.java)
                    )
                }
        }


    fun getRoomQuestions(roomId: String): Set<Int> =
        mapper.readValue(jedis.get("${ROOM_QUESTIONS}:${roomId}"), Set::class.java) as Set<Int>

    fun getRoomBackupSize(roomId: String): Int =
        jedis.get("${ROOM_BACKUP_QUESTIONS_SIZE}:$roomId").toInt()

    fun incrementRoomBackupSize(roomId: String): Boolean {
        jedis.incr("${ROOM_BACKUP_QUESTIONS_SIZE}:$roomId")
        return true
    }

    fun removePlayerFromRoom(roomId: String, user: User): Int =
        with(jedis) {
            lrem("$ROOM_PLAYERS:$roomId", 1, mapper.writeValueAsString(user))
            decr("$ROOM_PLAYER_COUNT:$roomId").toInt()
        }

    fun deleteRoom(roomId: String) =
        with(jedis) {
            del("$ROOM_PLAYERS:$roomId")
            del("$ROOM_PLAYER_COUNT:$roomId")
            del("$ROOM_QUESTIONS:$roomId")
            del("$ROOM_BACKUP_QUESTIONS_SIZE:$roomId")
            del("$ROOM_DETAILS:$roomId")
        }


    fun getBackupQuestion(roomId: String): List<Question> {
        val currentQuestionIds = getRoomQuestions(roomId)
        val roomDetails: CreateGameRequest = getRoomDetails(roomId)

        val newQuestionIds = randomUtil.randomNumberInSetButNotInOtherSet(
            questionDetailsRepository.getFilteredQuestionIds(
                roomDetails.subject,
                roomDetails.grade,
                roomDetails.difficulty,
                roomDetails.topics
            ),
            currentQuestionIds,
            size = 2
        )
        val newQuestions = currentQuestionIds + newQuestionIds
        jedis.setex(
            "$ROOM_QUESTIONS:$roomId", MAX_TTL, mapper.writeValueAsString(
                newQuestions
            )
        )
        return questionRepository.findAllById(newQuestionIds)
    }

    fun incrementPlayerScore(roomId: String, user: User): User {
        val players: List<User> = getRoomPlayers(roomId)
        val player = players.find { it.id == user.id }?.let {
            it.score++
            it.numberCorrect++
            it.isCorrect = true
            updateRoomPlayer(roomId, oldUser = user, newUser = it)
            it
        }

        return requireNotNull(player) {
            throw Exception(CommonExceptions.USER_NOT_FOUND)
        }
    }

    fun decrementPlayerScore(roomId: String, user: User): User {
        val players: List<User> = getRoomPlayers(roomId)
        val player = players.find { it.id == user.id }?.let {
            it.score = Integer.max(0, it.score - 1)
            it.numberIncorrect++
            it.isCorrect = false
            updateRoomPlayer(roomId, oldUser = user, newUser = it)
            it
        }

        return requireNotNull(player) {
            throw Exception(CommonExceptions.USER_NOT_FOUND)
        }
    }

    private fun updateRoomPlayer(roomId: String, oldUser: User, newUser: User) {
        removePlayerFromRoom(roomId, oldUser)
        addPlayerToRoom(roomId, newUser)
    }

}

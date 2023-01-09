package com.wahabahmad.mula.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wahabahmad.mula.exception.CommonExceptions
import com.wahabahmad.mula.model.Question
import com.wahabahmad.mula.model.User
import com.wahabahmad.mula.repository.QuestionRepository
import com.wahabahmad.mula.service.GameService.Companion.QUESTION_SET_SIZE
import org.springframework.stereotype.Service
import redis.clients.jedis.Jedis
import java.lang.Integer.max
import java.util.*

@Service
class SessionUtil(
    private val questionRepository: QuestionRepository,
    private val randomUtil: RandomUtil,
    private val jedis: Jedis
) {

    private val mapper = jacksonObjectMapper()

    companion object {
        const val OPEN_SESSION = "session:type:open"
        const val SESSION_PLAYERS = "session:players"
        const val SESSION_PLAYER_COUNT = "session:playerCount"
        const val SESSION_QUESTIONS = "session:questions"

        const val SESSION_BACKUP_QUESTIONS_SIZE = "session:backupQuestionSize"
        const val MAX_TTL: Long = 6 * 60 * 60 // 6hrs
    }

    fun openSessionExists(): Boolean =
        jedis.exists(OPEN_SESSION)

    fun createOpenSession(): String =
        with(UUID.randomUUID().toString()) {
            jedis.setex(OPEN_SESSION, MAX_TTL, this)
            jedis.setex("${SESSION_PLAYER_COUNT}:${this}", MAX_TTL, "0")
            jedis.setex(
                "${SESSION_QUESTIONS}:${this}", MAX_TTL,
                mapper.writeValueAsString(
                    randomUtil.randomDistinctInts(
                        1, questionRepository.count().toInt(), QUESTION_SET_SIZE
                    )
                )
            )
            return this
        }

    fun closeSession(sessionId: String): Long =
        with(sessionId) {
            jedis.setex("$SESSION_BACKUP_QUESTIONS_SIZE:$sessionId", MAX_TTL, "0")
            jedis.del(OPEN_SESSION)
        }

    fun addPlayerToSession(sessionId: String, user: User): String =
        with(sessionId) {
            jedis.incr("${SESSION_PLAYER_COUNT}:$this")
            jedis.lpush(
                "${SESSION_PLAYERS}:${this}",
                mapper.writeValueAsString(user)
            )
            jedis.expire("${SESSION_PLAYERS}:$this", MAX_TTL)
            this
        }

    fun addPlayerToOpenSession(user: User): String =
        with(jedis.get(OPEN_SESSION)) {
            jedis.incr("${SESSION_PLAYER_COUNT}:${this}")
            jedis.lpush(
                "${SESSION_PLAYERS}:${this}",
                mapper.writeValueAsString(user)
            )
            jedis.expire("${SESSION_PLAYERS}:$this", MAX_TTL)
            this
        }

    fun getSessionPlayers(sessionId: String): List<User> =
        mutableListOf<User>().apply {
            jedis.lrange("${SESSION_PLAYERS}:${sessionId}", 0, -1)
                .forEach { user ->
                    add(
                        mapper.readValue(user, User::class.java)
                    )
                }
        }

    fun getSessionQuestions(sessionId: String): Set<Int> =
        mapper.readValue(jedis.get("${SESSION_QUESTIONS}:${sessionId}"), Set::class.java) as Set<Int>

    fun getSessionBackupSize(sessionId: String): Int =
        jedis.get("$SESSION_BACKUP_QUESTIONS_SIZE:$sessionId").toInt()

    fun incrementSessionBackupSize(sessionId: String): Boolean {
        jedis.incr("$SESSION_BACKUP_QUESTIONS_SIZE:$sessionId")
        return true
    }

    fun removePlayerFromSession(sessionId: String, user: User): Int =
        with(jedis) {
            lrem("${SESSION_PLAYERS}:${sessionId}", 1, mapper.writeValueAsString(user))
            decr("${SESSION_PLAYER_COUNT}:${sessionId}").toInt()
        }

    fun deleteSession(sessionId: String): Long =
        with(jedis) {
            del("${SESSION_PLAYERS}:$sessionId")
            del("${SESSION_PLAYER_COUNT}:$sessionId")
            del("${SESSION_QUESTIONS}:$sessionId")
            del("$SESSION_BACKUP_QUESTIONS_SIZE:$sessionId")
        }

    fun getBackupQuestion(sessionId: String): List<Question> {
        val currentQuestionIds = getSessionQuestions(sessionId)
        val newQuestionIds = randomUtil.randomNumberNotInSet(
            min = 1,
            max = questionRepository.count().toInt(),
            size = 2,
            currentQuestionIds
        )
        val newQuestions = currentQuestionIds + newQuestionIds
        jedis.setex(
            "$SESSION_QUESTIONS:$sessionId", MAX_TTL, mapper.writeValueAsString(
                newQuestions
            )
        )
        return questionRepository.findAllById(newQuestionIds)
    }

    fun incrementPlayerScore(sessionId: String, user: User): User {
        val players: List<User> = getSessionPlayers(sessionId)
        val player = players.find { it.id == user.id }?.let {
            it.score++
            it.numberCorrect++
            it.isCorrect = true
            updateSessionPlayer(sessionId, oldUser = user, newUser = it)
            it
        }

        return requireNotNull(player) {
            throw Exception(CommonExceptions.USER_NOT_FOUND)
        }
    }

    fun decrementPlayerScore(sessionId: String, user: User): User {
        val players: List<User> = getSessionPlayers(sessionId)
        val player = players.find { it.id == user.id }?.let {
            it.score = max(0, it.score - 1)
            it.numberIncorrect++
            it.isCorrect = false
            updateSessionPlayer(sessionId, oldUser = user, newUser = it)
            it
        }

        return requireNotNull(player) {
            throw Exception(CommonExceptions.USER_NOT_FOUND)
        }
    }

    private fun updateSessionPlayer(sessionId: String, oldUser: User, newUser: User) {
        removePlayerFromSession(sessionId, oldUser)
        addPlayerToSession(sessionId, newUser)
    }
}
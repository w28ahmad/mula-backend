package com.wahabahmad.mula.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wahabahmad.mula.data.User
import com.wahabahmad.mula.repository.QuestionRepository
import com.wahabahmad.mula.service.GameService.Companion.QUESTION_SET_SIZE
import org.springframework.stereotype.Service
import redis.clients.jedis.Jedis
import java.util.*

@Service
class SessionUtil(
    private val questionRepository: QuestionRepository,
    private val randomUtil : RandomUtil,
    private val jedis: Jedis
) {

    private val mapper = jacksonObjectMapper()

    companion object {
        const val OPEN_SESSION = "session:type:open"
        const val SESSION_PLAYERS = "session:players"
        const val SESSION_PLAYER_COUNT = "session:playerCount"
        const val SESSION_QUESTIONS = "session:questions"

        const val SESSION_MAX_SIZE = 2
        const val MAX_TTL : Long = 6 * 60 * 60 // 6hrs
    }

    fun openSessionExists(): Boolean =
        jedis.exists(OPEN_SESSION)

    fun sessionPlayerSize(sessionId: String): Int =
        jedis.get("${SESSION_PLAYER_COUNT}:${sessionId}").toInt()

    fun openSessionPlayerSize(): Int =
        sessionPlayerSize(jedis.get(OPEN_SESSION))

    fun createOpenSession(): String =
        with(UUID.randomUUID().toString()) {
            jedis.setex(OPEN_SESSION, MAX_TTL, this)
            jedis.setex("${SESSION_PLAYER_COUNT}:${this}", MAX_TTL, "0")
            jedis.setex("${SESSION_QUESTIONS}:${this}", MAX_TTL,
            mapper.writeValueAsString(randomUtil.randomDistinctInts(
                1, questionRepository.count().toInt(), QUESTION_SET_SIZE
            )))
            return this
        }

    fun closeSession(): Long = jedis.del(OPEN_SESSION)

    fun addPlayerToOpenSession(user : User): String =
        with(jedis.get(OPEN_SESSION)) {
            jedis.incr("${SESSION_PLAYER_COUNT}:${this}")
            jedis.lpush("${SESSION_PLAYERS}:${this}",
                mapper.writeValueAsString(user))
            this
        }

    fun getSessionPlayers(sessionId: String): List<User> =
        mutableListOf<User>().apply {
            jedis.lrange("${SESSION_PLAYERS}:${sessionId}", 0, -1)
                .forEach{user -> add(
                    mapper.readValue(user, User::class.java)
                )}
        }
    
    fun getSessionQuestions(sessionId: String): Set<Int> =
        mapper.readValue(jedis.get("${SESSION_QUESTIONS}:${sessionId}"), Set::class.java) as Set<Int>

    fun removePlayerFromSession(sessionId: String, user: User) : Int =
        with(jedis) {
            lrem("${SESSION_PLAYERS}:${sessionId}", 1, mapper.writeValueAsString(user))
            decr("${SESSION_PLAYER_COUNT}:${sessionId}").toInt()
        }

    fun deleteSession(sessionId : String): Long =
        with(jedis) {
            del("${SESSION_PLAYERS}:${sessionId}")
            del("${SESSION_PLAYER_COUNT}:${sessionId}")
            del("${SESSION_QUESTIONS}:${sessionId}")
        }
}
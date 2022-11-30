package com.wahabahmad.mula.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wahabahmad.mula.data.User
import org.springframework.stereotype.Service
import redis.clients.jedis.Jedis
import java.util.*

@Service
class SessionUtil(
    private val jedis: Jedis
) {

    private val mapper = jacksonObjectMapper()

    companion object {
        const val OPEN_SESSION = "session:type:open"
        const val SESSION_PLAYERS = "session:players"
        const val SESSION_PLAYER_COUNT = "session:playerCount"

        const val SESSION_MAX_SIZE = 2
//        const val MAX_TTL = 6 * 60 * 60 // 6hr
    }

    fun openSessionExists(): Boolean =
        jedis.exists(OPEN_SESSION)

    fun sessionPlayerSize(sessionId: String): Int =
        jedis.get("${SESSION_PLAYER_COUNT}:${sessionId}").toInt()

    fun openSessionPlayerSize(): Int =
        sessionPlayerSize(jedis.get(OPEN_SESSION))

    fun createOpenSession(): String =
        with(UUID.randomUUID().toString()) {
            jedis.set(OPEN_SESSION, this)
            jedis.set("${SESSION_PLAYER_COUNT}:${this}", "0")
            return this
        }

    fun closeSession(): Long = jedis.del(OPEN_SESSION)

    fun addPlayerToOpenSession(user : User): String =
        with(jedis.get(OPEN_SESSION)) {
            jedis.lpush("${SESSION_PLAYERS}:${this}",
                mapper.writeValueAsString(user))
            jedis.incr("${SESSION_PLAYER_COUNT}:${this}")
            this
        }

    fun getSessionPlayers(sessionId: String): List<User> =
        mutableListOf<User>().apply {
            jedis.lrange("${SESSION_PLAYERS}:${sessionId}", 0, -1)
                .forEach{user -> add(
                    mapper.readValue(user, User::class.java)
                )}
        }

    fun removePlayerFromSession(sessionId: String, userId: String) : Int =
        with(jedis) {
            lrem("${SESSION_PLAYERS}:${sessionId}", 1, userId)
            decr("${SESSION_PLAYER_COUNT}:${sessionId}").toInt()
        }

    fun deleteSession(sessionId : String) =
        with(jedis) {
            del("${SESSION_PLAYERS}:${sessionId}")
            del("${SESSION_PLAYER_COUNT}:${sessionId}")
        }
}
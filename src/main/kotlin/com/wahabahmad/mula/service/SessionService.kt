package com.wahabahmad.mula.service

import com.wahabahmad.mula.model.User
import com.wahabahmad.mula.response.GameStartResponse
import com.wahabahmad.mula.response.PlayerConnectionResponse
import com.wahabahmad.mula.response.PlayerDisconnectionResponse
import com.wahabahmad.mula.util.SessionUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class SessionService(
    private val sessionUtil: SessionUtil,
    private val messagingTemplate: SimpMessagingTemplate,
) {

    companion object {
        const val GAME_CONNECTION_DELAY_SECONDS = 10
    }

    var openSessionTimer: Job? = null
    var startTime: Long = 0L

    fun connect(user: User): PlayerConnectionResponse =
        with(sessionUtil) {
            user.id = UUID.randomUUID().toString()
            if (!openSessionExists()) {
                val sessionId = createOpenSession()
                startTime = System.currentTimeMillis()
                openSessionTimer = GlobalScope.launch {
                    delay(GAME_CONNECTION_DELAY_SECONDS * 1000L)
                    closeSession(sessionId)
                    broadcastBeginGame()
                }
            }
            val remainingTime = getRemainingTime()
            val sessionId = addPlayerToOpenSession(user)
            PlayerConnectionResponse(
                sessionId = sessionId,
                users = getSessionPlayers(sessionId),
                remainingTime = remainingTime.toInt()
            )
        }

    private fun broadcastBeginGame() =
        messagingTemplate.convertAndSend("/topic/connect", GameStartResponse())

    private fun getRemainingTime(): Long =
        GAME_CONNECTION_DELAY_SECONDS - (System.currentTimeMillis() - startTime) / 1000L

    fun disconnect(sessionId: String, users: List<User>) =
        with(sessionUtil) {
            users.forEach { user ->
                if (removePlayerFromSession(sessionId, user) == 0) {
                    deleteSession(sessionId)
                    openSessionTimer?.cancel()
                }
            }
            PlayerDisconnectionResponse(sessionId = sessionId, users = getSessionPlayers(sessionId))
        }
}
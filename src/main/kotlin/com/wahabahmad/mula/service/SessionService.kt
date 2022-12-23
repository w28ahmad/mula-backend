package com.wahabahmad.mula.service

import com.wahabahmad.mula.data.User
import com.wahabahmad.mula.response.PlayerConnectionResponse
import com.wahabahmad.mula.response.PlayerDisconnectionResponse
import com.wahabahmad.mula.util.SessionUtil
import org.springframework.stereotype.Service
import java.util.*

@Service
class SessionService(
    private val sessionUtil: SessionUtil
) {

    fun connect(user: User): PlayerConnectionResponse =
        with(sessionUtil) {
            user.id = UUID.randomUUID().toString()
            if(!openSessionExists()) createOpenSession()
            val sessionId = addPlayerToOpenSession(user)
            PlayerConnectionResponse(sessionId=sessionId, users=getSessionPlayers(sessionId))
        }

    fun close(sessionId: String) = sessionUtil.closeSession()

    fun disconnect(sessionId: String, users: List<User>) =
        with(sessionUtil) {
            users.forEach{user ->
                if(removePlayerFromSession(sessionId, user.id!!)==0)
                    deleteSession(sessionId)
            }
            PlayerDisconnectionResponse(sessionId=sessionId, users=getSessionPlayers(sessionId))
        }
}
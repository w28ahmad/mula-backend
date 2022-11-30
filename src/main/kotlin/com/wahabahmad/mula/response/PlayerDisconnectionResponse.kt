package com.wahabahmad.mula.response

import com.wahabahmad.mula.data.SocketMessageTypes
import com.wahabahmad.mula.data.User

data class PlayerDisconnectionResponse (
    val type: SocketMessageTypes = SocketMessageTypes.PLAYER_DISCONNECTION,
    val sessionId: String,
    val users: List<User>
)

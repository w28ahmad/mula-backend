package com.wahabahmad.mula.response

import com.wahabahmad.mula.data.SocketMessageTypes
import com.wahabahmad.mula.data.User

data class PlayerConnectionResponse(
    val type: SocketMessageTypes = SocketMessageTypes.PLAYER_CONNECTION,
    val sessionId: String,
    val users: List<User>
)

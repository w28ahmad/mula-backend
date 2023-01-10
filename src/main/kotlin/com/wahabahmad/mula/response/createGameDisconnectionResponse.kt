package com.wahabahmad.mula.response

import com.wahabahmad.mula.data.SocketMessageTypes
import com.wahabahmad.mula.model.User

data class createGameDisconnectionResponse(
    val type: SocketMessageTypes = SocketMessageTypes.PLAYER_DISCONNECTION,
    val roomId: String,
    val users: List<User>
)
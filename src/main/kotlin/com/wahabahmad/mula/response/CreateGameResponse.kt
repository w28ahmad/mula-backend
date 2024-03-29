package com.wahabahmad.mula.response

import com.wahabahmad.mula.data.SocketMessageTypes
import com.wahabahmad.mula.model.User

class CreateGameResponse(
    val type: SocketMessageTypes = SocketMessageTypes.PLAYER_CONNECTION,
    val roomId: String,
    val users: List<User>
)
package com.wahabahmad.mula.response

import com.wahabahmad.mula.data.SocketMessageTypes

data class GameStartResponse (
    val type: SocketMessageTypes = SocketMessageTypes.START_GAME
)
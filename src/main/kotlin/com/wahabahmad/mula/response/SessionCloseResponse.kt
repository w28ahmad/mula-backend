package com.wahabahmad.mula.response

import com.wahabahmad.mula.data.SocketMessageTypes

data class SessionCloseResponse(
    val type: SocketMessageTypes = SocketMessageTypes.SESSION_CLOSE,
)
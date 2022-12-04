package com.wahabahmad.mula.request

import com.wahabahmad.mula.data.User

data class PlayerDisconnectRequest(
    val sessionId: String,
    val users: List<User>
)

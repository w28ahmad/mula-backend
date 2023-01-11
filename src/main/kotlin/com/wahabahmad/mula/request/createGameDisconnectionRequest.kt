package com.wahabahmad.mula.request

import com.wahabahmad.mula.model.User

data class createGameDisconnectionRequest(
    val roomId: String,
    val users: List<User>
)

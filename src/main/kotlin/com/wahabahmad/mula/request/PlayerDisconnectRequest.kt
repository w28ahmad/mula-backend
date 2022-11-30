package com.wahabahmad.mula.request

import com.wahabahmad.mula.data.User

data class PlayerDisconnectRequest(
    val id: String,
    val users: List<User>
)

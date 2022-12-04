package com.wahabahmad.mula.controller

import com.wahabahmad.mula.data.User
import com.wahabahmad.mula.request.PlayerDisconnectRequest
import com.wahabahmad.mula.response.PlayerConnectionResponse
import com.wahabahmad.mula.response.PlayerDisconnectionResponse
import com.wahabahmad.mula.service.SessionService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.RestController

@RestController
class SessionController(
    private val sessionService: SessionService,
) {

    @MessageMapping("/connect")
    @SendTo("/topic/connect")
    fun connect(user: User): PlayerConnectionResponse =
        sessionService.connect(user)

    @MessageMapping("/disconnect")
    @SendTo("/topic/disconnect")
    fun disconnect(player: PlayerDisconnectRequest): PlayerDisconnectionResponse =
        sessionService.disconnect(player.sessionId, player.users)
}
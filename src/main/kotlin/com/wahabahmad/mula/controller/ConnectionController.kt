package com.wahabahmad.mula.controller

import com.wahabahmad.mula.data.User
import com.wahabahmad.mula.service.ConnectionService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.RestController

@RestController
class ConnectionController(
    private val connectionService: ConnectionService
) {
    @MessageMapping("/connection")
    @SendTo("/topic/connection")
    fun connect(user: User) : List<User> {
        println(user.toString())
        return connectionService.connect(user)
    }
}
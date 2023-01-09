package com.wahabahmad.mula.controller

import com.wahabahmad.mula.request.CreateGameRequest
import com.wahabahmad.mula.response.CreateGameResponse
import com.wahabahmad.mula.service.CreateGameService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CreateGameController(
    private val createGameService: CreateGameService
) {
    @PostMapping("/createGame")
    fun createGame(@RequestBody createGameRequest: CreateGameRequest): CreateGameResponse =
        with(createGameRequest) {
            println(createGameRequest)
            CreateGameResponse("testing")
        }
}
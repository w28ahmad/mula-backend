package com.wahabahmad.mula.service

import com.wahabahmad.mula.model.User
import com.wahabahmad.mula.response.CreateGameResponse
import com.wahabahmad.mula.util.RoomUtil
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreateGameService(
    private val roomUtil: RoomUtil
) {


    fun createGame(user: User): CreateGameResponse =
        with(sessionUtil) {
            user.id = UUID.randomUUID().toString()

            CreateGameResponse(/* TODO */)
        }


}
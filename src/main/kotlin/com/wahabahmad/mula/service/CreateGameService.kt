package com.wahabahmad.mula.service

import com.wahabahmad.mula.model.User
import com.wahabahmad.mula.response.CreateGameResponse
import com.wahabahmad.mula.response.createGameDisconnectionResponse
import com.wahabahmad.mula.util.RoomUtil
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreateGameService(
    private val roomUtil: RoomUtil
) {


    fun createGameConnect(
        user: User,
        difficulty: List<String>,
        grade: Int,
        subject: String,
        topics: List<String>
    ): CreateGameResponse =
        with(roomUtil) {
            user.id = UUID.randomUUID().toString()
            val roomId = roomUtil.createRoom(difficulty, grade, subject, topics)
            addPlayerToRoom(roomId, user)
            return CreateGameResponse(
                roomId = roomId,
                users = getRoomPlayers(roomId)
            )
        }


    fun createGameDisconnect(roomId: String, users: List<User>) =
        with(roomUtil) {
            users.forEach { user ->
                if (removePlayerFromRoom(roomId, user) == 0) {
                    deleteRoom(roomId)
                }
            }
            createGameDisconnectionResponse(
                roomId = roomId,
                users = getRoomPlayers(roomId)
            )
        }


}
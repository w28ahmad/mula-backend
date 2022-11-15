package com.wahabahmad.mula.service

import com.wahabahmad.mula.data.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class ConnectionService {

    private val connectedUsers : MutableList<User> = mutableListOf()
    private val MAX_SIZE = 2

    fun connect(user: User): MutableList<User> {
        user.id = UUID.randomUUID().toString()
        if (connectedUsers.size >= MAX_SIZE)
            connectedUsers.clear()
        connectedUsers.add(user)
        return connectedUsers
    }
}
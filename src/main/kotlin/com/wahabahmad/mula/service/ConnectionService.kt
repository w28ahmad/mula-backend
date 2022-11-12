package com.wahabahmad.mula.service

import com.wahabahmad.mula.data.User
import org.springframework.stereotype.Service

@Service
class ConnectionService {

    private val connectedUsers : MutableList<User> = mutableListOf()
    private val MAX_SIZE = 5

    fun connect(user: User): MutableList<User> {
        if (connectedUsers.size >= MAX_SIZE)
            connectedUsers.clear()
        connectedUsers.add(user)
        return connectedUsers
    }
}
package com.example.gchat.domain.chat

import com.example.gchat.data.local.ChatManager
import com.example.gchat.data.local.dao.ChatDao
import com.example.gchat.data.local.entities.ChatMessage

class ChatManagerImpl(private val chatDao: ChatDao) : ChatManager {
    override suspend fun insertMessage(message: ChatMessage) {
        chatDao.insertMessage(message = message)
    }

    override suspend fun getMessagesBetweenUsers(
        senderAddress: String
    ): List<ChatMessage> = chatDao.getMessagesBetweenUsers(
        senderAddress = senderAddress
    )

    override suspend fun getSenders(): List<String> =
        chatDao.getSenders()
}
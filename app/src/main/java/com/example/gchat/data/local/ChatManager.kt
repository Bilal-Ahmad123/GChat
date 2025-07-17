package com.example.gchat.data.local

import com.example.gchat.data.local.entities.ChatMessage

interface ChatManager {
    suspend fun insertMessage(message: ChatMessage)
    suspend fun getMessagesBetweenUsers( senderAddress: String): List<ChatMessage>
    suspend fun getSenders(): List<String>
}
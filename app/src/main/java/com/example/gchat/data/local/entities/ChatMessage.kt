package com.example.gchat.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages")
data class ChatMessage(
    @PrimaryKey(autoGenerate = true) val messageId: Int = 0,
    val senderDeviceAddress: String,
    val receiverDeviceAddress: String,
    val message: String,
    val timestamp: Long,
    val isMineMessage: Boolean = true
)
package com.example.gchat.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "chat_messages")
data class ChatMessage(
    @PrimaryKey(autoGenerate = true) val messageId: Long = 0L,
    val senderDeviceAddress: String,
    val receiverDeviceAddress: String,
    val message: String,
    val timestamp: Long,
    val isMineMessage: Boolean = true
)
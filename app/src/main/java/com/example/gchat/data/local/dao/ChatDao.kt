package com.example.gchat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gchat.data.local.entities.ChatMessage

@Dao
interface ChatDao {
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessage)

    @Query("Select * from chat_messages where (receiverDeviceAddress =:receiverAddress) ORDER by timestamp ASC")
    suspend fun getMessagesBetweenUsers(receiverAddress: String, senderAddress: String): List<ChatMessage>

    @Query("Select DISTINCT senderDeviceAddress from chat_messages")
    suspend fun getAllChats(senderAddress: String): List<ChatMessage>
}
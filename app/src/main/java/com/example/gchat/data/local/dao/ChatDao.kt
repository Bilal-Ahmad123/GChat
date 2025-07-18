package com.example.gchat.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gchat.data.local.entities.ChatMessage

@Dao
interface ChatDao {
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    fun insertMessage(message: ChatMessage)

    @Query("Select * from chat_messages where (senderDeviceAddress =:senderAddress) ORDER by timestamp ASC")
    fun getMessagesBetweenUsers( senderAddress: String): List<ChatMessage>

    @Query("Select DISTINCT senderDeviceAddress from chat_messages")
    fun getSenders(): List<String>

    @Query("Select * from chat_messages")
    fun getAllMessages(): List<ChatMessage>
}
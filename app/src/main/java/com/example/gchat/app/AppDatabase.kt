package com.example.gchat.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gchat.data.local.dao.ChatDao
import com.example.gchat.data.local.entities.ChatMessage

@Database(entities = [ChatMessage::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
}

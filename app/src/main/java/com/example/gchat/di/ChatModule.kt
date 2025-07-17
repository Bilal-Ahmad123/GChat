package com.example.gchat.di

import android.content.Context
import androidx.room.Room
import com.example.gchat.app.AppDatabase
import com.example.gchat.data.local.ChatManager
import com.example.gchat.data.local.dao.ChatDao
import com.example.gchat.domain.chat.ChatManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ChatModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context,AppDatabase::class.java,"gchat_db").build()
    }

    @Provides
    @Singleton
    fun provideChatDao(appDatabase: AppDatabase) = appDatabase.chatDao()

    @Provides
    @Singleton
    fun provideChatManager(chatDao: ChatDao): ChatManager {
        return ChatManagerImpl(chatDao)
    }
}
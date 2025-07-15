package com.example.gchat.di

import android.content.Context
import com.example.gchat.data.bluetooth.BluetoothStateReader
import com.example.gchat.domain.bluetooth.BluetoothStateReaderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BluetoothModule {

    @Provides
    fun provideContext(
        @ApplicationContext context: Context,
    ): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideBluetoothStateReader(context: Context): BluetoothStateReader {
        return BluetoothStateReaderImpl(context)
    }

}
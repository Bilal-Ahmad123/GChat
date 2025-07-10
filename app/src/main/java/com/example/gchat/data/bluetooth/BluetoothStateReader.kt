package com.example.gchat.data.bluetooth

import android.content.Context

interface BluetoothStateReader {
    fun checkBluetoothExists(): Boolean
    fun checkBluetoothEnabled(): Boolean?
}
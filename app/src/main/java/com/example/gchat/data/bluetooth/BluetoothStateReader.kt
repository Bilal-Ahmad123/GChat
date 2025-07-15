package com.example.gchat.data.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import kotlinx.coroutines.flow.Flow

interface BluetoothStateReader {
    fun checkBluetoothExists(): Boolean
    fun checkBluetoothEnabled(): Boolean?
    fun getBluetoothAdapterInstance(): BluetoothAdapter?
    fun startBluetoothDiscoveryService()
    fun createServerSocket()
    fun startListeningServerSocket()
    fun createClientSocket()
    fun sendBluetoothRequest(deviceAddress: String)
    fun listenMessages()
    fun getAlreadyPairedDevices(): Set<BluetoothDevice?>?
    fun sendMessage(message: String)
}
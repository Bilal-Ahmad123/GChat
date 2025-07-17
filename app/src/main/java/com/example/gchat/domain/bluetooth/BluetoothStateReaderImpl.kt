package com.example.gchat.domain.bluetooth

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat.getSystemService
import com.example.gchat.data.bluetooth.BluetoothStateReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID
import kotlinx.coroutines.flow.flow


class BluetoothStateReaderImpl(context: Context) :BluetoothStateReader {
     var bluetoothManager: BluetoothManager? = null
     var bluetoothAdapter: BluetoothAdapter? = null
     var serverSocket: BluetoothServerSocket? = null
     var clientSocket: BluetoothSocket? = null
     var socketConnected = MutableStateFlow<Boolean>(false)
     var messages = MutableStateFlow<String>("")
     var device: BluetoothDevice? = null
    private var mmInStream: InputStream? = null
    private var mmOutStream: OutputStream? = null

    private val mmBuffer: ByteArray = ByteArray(1024)
    init {
        bluetoothManager = getSystemService(context,BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager?.adapter
    }




    override fun checkBluetoothExists(): Boolean {
        return bluetoothAdapter != null
    }

    override fun checkBluetoothEnabled(): Boolean? {
        return bluetoothAdapter?.isEnabled
    }

    override fun getBluetoothAdapterInstance(): BluetoothAdapter? {
        return bluetoothAdapter
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    override fun startBluetoothDiscoveryService() {
        bluetoothAdapter?.startDiscovery();
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    override fun createServerSocket() {
       serverSocket = bluetoothAdapter?.listenUsingRfcommWithServiceRecord("GChat",UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") )
    }

    override fun startListeningServerSocket() {
        var shouldLoop = true
        while (shouldLoop) {
            val socket: BluetoothSocket? = try {
                serverSocket?.accept()
            } catch (e: Exception) {
                Log.e("ServerSocket", "Socket's accept() method failed", e)
                shouldLoop = false
                null
            }
            socket?.also {
                Log.d("ServerSocket", "Socket connected")
                clientSocket = it
                initializeStreams()
                listenMessages()
                serverSocket?.close()
                shouldLoop = false
            }
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    override fun createClientSocket() {
        clientSocket = device?.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    override fun sendBluetoothRequest(deviceAddress: String) {
        CoroutineScope(Dispatchers.IO).launch {
            device = bluetoothAdapter?.getRemoteDevice(deviceAddress)
            createClientSocket()
            try {
                clientSocket?.connect()
                initializeStreams()
                listenMessages()
            } catch (exp: Exception) {
                Log.d("BluetoothStateReaderImpl", "Socket's connect() method failed", exp)
            }
        }
    }

    private fun initializeStreams(){
        mmInStream = clientSocket?.inputStream
        mmOutStream = clientSocket?.outputStream
    }

    override fun listenMessages(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var numBytes: Int
                while (clientSocket != null && clientSocket!!.isConnected) {
                    numBytes = try {
                        mmInStream?.read(mmBuffer)
                    } catch (e: Exception) {
                        break
                    }!!
                    val message = String(mmBuffer, 0, numBytes)
                    if (message.isNotEmpty()) {
                        messages.emit(message)
                        Log.d("BluetoothStateReaderImpl", "Received message: $message")
                    }
                }
            } catch (exp: Exception) {
                Log.d("BluetoothStateReaderImpl", "Input stream was disconnected", exp)
            }
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    override fun getAlreadyPairedDevices(): Set<BluetoothDevice?>? {
      return bluetoothAdapter?.bondedDevices
    }

    override fun sendMessage(message: String) {
        Log.d("BluetoothStateReaderImpl", "Sending message: $message")
        try {
            mmOutStream!!.write(message.toByteArray())
        }
        catch (exp: Exception){
            Log.d("BluetoothStateReaderImpl", "Output stream was disconnected", exp)
        }
    }

    override fun getMessages(): Flow<String> {
        return messages
    }


}
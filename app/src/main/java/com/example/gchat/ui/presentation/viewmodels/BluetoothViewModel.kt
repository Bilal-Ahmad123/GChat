package com.example.gchat.ui.presentation.viewmodels

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gchat.data.bluetooth.BluetoothStateReader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BluetoothViewModel @Inject constructor(private val bluetoothStateReader: BluetoothStateReader) :
    ViewModel() {
    val bluetoothEnabled = MutableStateFlow<Boolean?>(false)
    val bluetoothExists = MutableStateFlow<Boolean>(false)
    val socketConnected = MutableStateFlow<Boolean>(false)
    val messages = MutableStateFlow<String>("")
    val alreadyPairedDevices = MutableStateFlow<Set<BluetoothDevice?>?>(null)
    fun checkBluetoothExists() {
        viewModelScope.launch(Dispatchers.IO) {
            bluetoothExists.emit(bluetoothStateReader.checkBluetoothExists())
        }
    }

    fun checkBluetoothEnabled() {
        viewModelScope.launch(Dispatchers.IO) {
            bluetoothEnabled.emit(bluetoothStateReader.checkBluetoothEnabled())
        }
    }

    fun startBluetoothDiscoveryService() {
        viewModelScope.launch(Dispatchers.IO) {
            bluetoothStateReader.startBluetoothDiscoveryService()
        }
    }

    fun createServerSocket() {
//        viewModelScope.launch(Dispatchers.IO) {
            bluetoothStateReader.createServerSocket()
//        }
    }

    fun startListeningServerSocket() {
        viewModelScope.launch(Dispatchers.IO) {
            bluetoothStateReader.startListeningServerSocket()
        }
    }

    fun createClientSocket() {
        viewModelScope.launch(Dispatchers.IO) {
            bluetoothStateReader.createClientSocket()
        }
    }

    fun sendBluetoothRequest(deviceAddress: String) {
        viewModelScope.launch(Dispatchers.IO) {
            bluetoothStateReader.sendBluetoothRequest(deviceAddress)
        }
    }

    fun getAlreadyPairedDevices(): Set<BluetoothDevice?>? {
        return bluetoothStateReader.getAlreadyPairedDevices()
    }

    fun listenMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            bluetoothStateReader.listenMessages()
        }
    }

    fun getMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            bluetoothStateReader.getMessages().collectLatest {
                messages.emit(it)
            }
        }
    }

    fun sendMessage(message: String) {
        bluetoothStateReader.sendMessage(message)
    }


    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
}
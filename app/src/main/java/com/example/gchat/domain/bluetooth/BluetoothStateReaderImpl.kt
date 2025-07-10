package com.example.gchat.domain.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import com.example.gchat.data.bluetooth.BluetoothStateReader

class BluetoothStateReaderImpl(context: Context) :BluetoothStateReader {
    private var bluetoothManager: BluetoothManager? = null
    private var bluetoothAdapter: BluetoothAdapter? = null

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
}
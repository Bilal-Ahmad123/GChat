package com.example.gchat.ui.presentation.devices

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gchat.domain.bluetooth.models.DeviceItem
import com.example.gchat.ui.presentation.viewmodels.BluetoothViewModel


@Preview
@Composable
fun DevicesPreview(bluetoothViewModel: BluetoothViewModel,navController: NavHostController) {
    Devices(bluetoothViewModel,navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Devices(bluetoothViewModel: BluetoothViewModel,navController: NavHostController) {
    val deviceList = remember { mutableStateListOf<DeviceItem>() }
    val alreadyPairedDevices = remember { mutableSetOf<BluetoothDevice>() }

    LazyColumn {

        items(deviceList.size) { item ->
            ElevatedCard(
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(vertical = 10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "${deviceList[item].name}",
                        )
                        Text(
                            text = "${deviceList[item].address}",
                        )

                    }
                    if(deviceList[item].alreadyBonded) {
                        FilledTonalButton(onClick = {
                            navController.navigate("CHATSCREEN/${deviceList[item].address}")
                        }) {
                            Text("Connect")
                        }
                    }
                    else{
                        FilledTonalButton(onClick = {
                            sendSocketRequest(
                                bluetoothViewModel,
                                deviceList[item].address.toString()
                            )
                        }) {
                            Text("Pair")
                        }
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        bluetoothViewModel.startBluetoothDiscoveryService()
        bluetoothViewModel.createServerSocket()
        bluetoothViewModel.startListeningServerSocket()
//        bluetoothViewModel.createClientSocket()

        addAlreadyPairedDevicesToList(bluetoothViewModel.getAlreadyPairedDevices()){deviceName,deviceAddress->
            if(deviceList.find { it.address == deviceAddress} == null){
                deviceList.add(DeviceItem(deviceName,deviceAddress,true))
            }
        }
    }
    discoverBluetoothDevices(bluetoothViewModel) { deviceName, deviceAddress ->
        if(deviceList.find { it.address == deviceAddress} == null){
            deviceList.add(DeviceItem(deviceName,deviceAddress))
        }
    }
}

private fun addAlreadyPairedDevicesToList(
    updateList1: Set<BluetoothDevice?>?,
    updateList: (String?, String?) -> Unit
){
    updateList1?.forEach {
        updateList(it?.name!!,it?.address)
    }
}

@Composable
fun discoverBluetoothDevices(
    bluetoothViewModel: BluetoothViewModel,
    updateList: (String?, String?) -> Unit
) {

    val bluetoothEnabled by bluetoothViewModel.bluetoothEnabled.collectAsState()
    if (bluetoothEnabled!!) {
        BluetoothScan(updateList)
    }
}


@Composable
fun BluetoothScan(updateList: (String?, String?) -> Unit) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val receiver = object : BroadcastReceiver() {

            @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
            override fun onReceive(context: Context, intent: Intent) {
                val action: String = intent.action.toString()
                when (action) {
                    BluetoothDevice.ACTION_FOUND -> {
                        val device: BluetoothDevice? =
                            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        val deviceName = device?.name
                        val deviceHardwareAddress = device?.address
                        Log.d("Bluetooth", "Device found: $deviceName, $deviceHardwareAddress")
                        updateList(deviceName, deviceHardwareAddress)
                    }
                }
            }
        }


        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(receiver, filter)

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }
}

fun sendSocketRequest(bluetoothViewModel: BluetoothViewModel,deviceAddress: String) {
    bluetoothViewModel.sendBluetoothRequest(deviceAddress)
}



package com.example.gchat.domain.bluetooth.models

data class DeviceItem(
    val name: String?,
    val address: String?,
    val alreadyBonded: Boolean = false
)
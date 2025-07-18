package com.example.gchat.ui.presentation.chat.chatscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gchat.data.local.entities.ChatMessage
import com.example.gchat.ui.presentation.viewmodels.BluetoothViewModel
import com.example.gchat.ui.presentation.viewmodels.ChatViewModel

@Composable
@Preview
fun ChatScreen(
    bluetoothViewModel: BluetoothViewModel,
    chatViewModel: ChatViewModel,
    deviceId: String
) {
    val message by bluetoothViewModel.messages.collectAsState()
    val socketConnected by bluetoothViewModel.socketConnected.collectAsState()
    val messages by chatViewModel.messages.collectAsState()
    val allMessages by chatViewModel.allMessages.collectAsState()

    val sampleMessages = remember {
        mutableStateListOf<ChatMessage>()
    }
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            items(sampleMessages.size) { message ->
                MessageBubble(sampleMessages[message])
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier
                    .height(56.dp),
                placeholder = { Text("Type a message...") },
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                maxLines = 1
            )

            IconButton(
                onClick = {
                    chatViewModel.insertMessage(
                        ChatMessage(
                            senderDeviceAddress = "user_1",
                            receiverDeviceAddress = deviceId,
                            message = inputText,
                            timestamp = System.currentTimeMillis(),
                        )
                    )
                    bluetoothViewModel.sendMessage(inputText)
                    sampleMessages.add(
                        ChatMessage(
                            senderDeviceAddress = "user_1",
                            receiverDeviceAddress = deviceId,
                            message = inputText,
                            timestamp = System.currentTimeMillis(),
                        )
                    )
                    inputText = ""
                }
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send")
            }
        }
    }

    LaunchedEffect(message) {
        Log.d("ChatScreen", message)
        chatViewModel.insertMessage(
            ChatMessage(
                senderDeviceAddress = "user_1",
                receiverDeviceAddress = deviceId,
                message = message,
                timestamp = System.currentTimeMillis(),
                isMineMessage = false
            )
        )
        sampleMessages.add(
            ChatMessage(
                senderDeviceAddress = "user_1",
                receiverDeviceAddress = deviceId,
                message = message,
                timestamp = System.currentTimeMillis(),
                isMineMessage = false
            )
        )
    }

    LaunchedEffect(Unit) {
        sendSocketRequest(bluetoothViewModel, deviceId)
        bluetoothViewModel.getMessages()
        chatViewModel.getMessagesBetweenUsers(deviceId)
        chatViewModel.getAllMessages()
    }

    LaunchedEffect(messages) {
        Log.d("ChatScreen", messages.toString())
        messages?.forEach {
            sampleMessages.add(it)
        }
    }

}

fun sendSocketRequest(bluetoothViewModel: BluetoothViewModel, deviceAddress: String) {
    bluetoothViewModel.sendBluetoothRequest(deviceAddress)
}


@Composable
fun MessageBubble(message: ChatMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (message.isMineMessage) Arrangement.End else Arrangement.Start

    ) {
        Box(
            modifier = Modifier
                .background(
                    if (message.isMineMessage) Color(0xFF87CEEB) else Color.LightGray,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        ) {
            Text(text = message.message, color = Color.White)
        }
    }
}

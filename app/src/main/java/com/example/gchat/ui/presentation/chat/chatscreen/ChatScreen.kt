package com.example.gchat.ui.presentation.chat.chatscreen

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

@Composable
@Preview
fun ChatScreen(bluetoothViewModel: BluetoothViewModel,deviceId: String) {
    val message by bluetoothViewModel.messages.collectAsState()
    val socketConnected by bluetoothViewModel.socketConnected.collectAsState()
    val sampleMessages = mutableListOf(
        ChatMessage(1, "user_1", "user_2", "Hey there!", 1620000000000),
        ChatMessage(2, "user_2", "user_1", "Hi! How are you?", 1620000001000),
        ChatMessage(3, "user_1", "user_2", "I'm good, just working on the project.", 1620000002000),
        ChatMessage(4, "user_2", "user_1", "Sounds good. Need any help?", 1620000003000),
        ChatMessage(5, "user_1", "user_2", "Maybe later. Thanks!", 1620000004000),
        ChatMessage(6, "user_2", "user_1", "Cool. Ping me anytime.", 1620000005000),
        ChatMessage(7, "user_1", "user_2", "Sure thing!", 1620000006000),
        ChatMessage(8, "user_2", "user_1", "BTW, check the email I sent you.", 1620000007000),
        ChatMessage(9, "user_1", "user_2", "Okay, will do.", 1620000008000),
        ChatMessage(10, "user_2", "user_1", "Thanks 👍", 1620000009000)
    )
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
            reverseLayout = true
        ) {
            items(sampleMessages.size) { message ->
                MessageBubble(sampleMessages[message].message)
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
                    bluetoothViewModel.sendMessage("hello there")
                }
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send")
            }
        }
    }

    LaunchedEffect(message) {
        sampleMessages.add(ChatMessage(11, "user_1", "user_2", message, System.currentTimeMillis()))
    }

    LaunchedEffect(Unit) {
        sendSocketRequest(bluetoothViewModel,deviceId)
    }
    LaunchedEffect(socketConnected) {
        if(socketConnected){
            Log.d("ChatScreen", socketConnected.toString())
        }
    }
}

fun sendSocketRequest(bluetoothViewModel: BluetoothViewModel,deviceAddress: String) {
    bluetoothViewModel.sendBluetoothRequest(deviceAddress)
}


@Composable
fun MessageBubble(message: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Text(text = message)
        }
    }
}

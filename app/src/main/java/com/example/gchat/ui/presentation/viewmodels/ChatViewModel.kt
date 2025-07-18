package com.example.gchat.ui.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gchat.data.local.ChatManager
import com.example.gchat.data.local.entities.ChatMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val chatManager: ChatManager):ViewModel() {
    val messages = MutableStateFlow<List<ChatMessage>?>(null)
    val senders = MutableStateFlow<List<String>?>(null)
    val allMessages = MutableStateFlow<List<ChatMessage>?>(null)
    fun insertMessage(chatMessage: ChatMessage){
        viewModelScope.launch(Dispatchers.IO){
            chatManager.insertMessage(chatMessage)
        }
    }

    fun getMessagesBetweenUsers(senderAddress: String){
        viewModelScope.launch(Dispatchers.IO){
            messages.emit(chatManager.getMessagesBetweenUsers( senderAddress))
        }
    }

    fun getSenders(){
        viewModelScope.launch(Dispatchers.IO){
            chatManager.getSenders()
        }
    }
    fun getAllMessages(){
        viewModelScope.launch(Dispatchers.IO){
            allMessages.emit(chatManager.getAllMessages())
        }
    }
}
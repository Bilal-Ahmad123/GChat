package com.example.gchat.ui.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gchat.data.local.ChatManager
import com.example.gchat.data.local.entities.ChatMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val chatManager: ChatManager):ViewModel() {
    fun insertMessage(chatMessage: ChatMessage){
        viewModelScope.launch(Dispatchers.IO){
            chatManager.insertMessage(chatMessage)
        }
    }

    fun getMessagesBetweenUsers(receiverAddress: String, senderAddress: String){
        viewModelScope.launch(Dispatchers.IO){
            chatManager.getMessagesBetweenUsers( senderAddress)
        }
    }

    fun getSenders(){
        viewModelScope.launch(Dispatchers.IO){
            chatManager.getSenders()
        }
    }
}
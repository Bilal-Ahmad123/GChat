package com.example.gchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.gchat.navigation.AppNavHost
import com.example.gchat.ui.presentation.viewmodels.BluetoothViewModel
import com.example.gchat.ui.presentation.viewmodels.ChatViewModel
import com.example.gchat.ui.theme.GChatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GChatTheme {
                val viewModel: BluetoothViewModel = hiltViewModel()
                val chatViewModel: ChatViewModel = hiltViewModel()
               MainScreen(viewModel,chatViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: BluetoothViewModel, chatViewModel: ChatViewModel){
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "Top App Bar")
            },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.ArrowBack, "backIcon")
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White,
            ),
        )
    }) { padding ->
        AppNavHost(
            modifier = Modifier.padding(padding),
            navController = rememberNavController(),
            viewModel = viewModel,
            chatViewModel = chatViewModel
        )
    }
}
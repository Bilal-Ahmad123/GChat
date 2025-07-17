package com.example.gchat.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gchat.ui.presentation.chat.chatscreen.ChatScreen
import com.example.gchat.ui.presentation.devices.DevicesPreview
import com.example.gchat.ui.presentation.splash.SplashScreen
import com.example.gchat.ui.presentation.home.HomeScreen
import com.example.gchat.ui.presentation.viewmodels.BluetoothViewModel
import com.example.gchat.ui.presentation.viewmodels.ChatViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Splash.route,
    viewModel: BluetoothViewModel,
    chatViewModel: ChatViewModel
){
    NavHost(modifier= modifier, navController = navController, startDestination = startDestination){
        composable(NavigationItem.Splash.route){
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Box() {
                    SplashScreen(modifier = Modifier.size(500.dp).align(Alignment.Center), navController = navController, bluetoothViewModel = viewModel)
                }
            }
        }
        composable(NavigationItem.Home.route) {
            HomeScreen(navController= navController,viewModel = viewModel)
        }
        composable(NavigationItem.Devices.route) {
            DevicesPreview(bluetoothViewModel = viewModel,navController = navController)
        }
        composable(NavigationItem.ChatScreen.route+"/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        )) {
            val id = it.arguments!!.getString("id")
            ChatScreen(bluetoothViewModel = viewModel,chatViewModel, id.toString())
        }
    }
}
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gchat.ui.presentation.SplashScreen
import com.example.gchat.ui.presentation.home.HomeScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Splash.route,
){
    NavHost(modifier= Modifier, navController = navController, startDestination = startDestination){
        composable(NavigationItem.Splash.route){
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Box() {
                    SplashScreen(modifier = Modifier.size(500.dp).align(Alignment.Center), navController = navController)
                }
            }
        }
        composable(NavigationItem.Home.route) {
            HomeScreen()
        }
    }
}
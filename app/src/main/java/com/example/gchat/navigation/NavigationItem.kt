package com.example.gchat.navigation

import com.example.gchat.common.Screen

sealed class NavigationItem(val route: String) {
    object Splash: NavigationItem(Screen.HOME.name)
}
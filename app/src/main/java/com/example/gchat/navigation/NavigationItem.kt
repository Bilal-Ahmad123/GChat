package com.example.gchat.navigation

import com.example.gchat.common.Screen

sealed class NavigationItem(val route: String) {
    object Splash: NavigationItem(Screen.SPLASH.name)
    object Home: NavigationItem(Screen.HOME.name)
    object Devices: NavigationItem(Screen.DEVICES.name)
    object Chats: NavigationItem(Screen.CHATS.name)
    object ChatList: NavigationItem(Screen.CHATLIST.name)
    object ChatScreen: NavigationItem(Screen.CHATSCREEN.name)
}
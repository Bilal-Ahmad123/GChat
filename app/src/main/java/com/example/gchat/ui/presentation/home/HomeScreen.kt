package com.example.gchat.ui.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gchat.R
import com.example.gchat.ui.presentation.viewmodels.BluetoothViewModel

@Composable
@Preview
fun HomeScreen(navController: NavController,viewModel: BluetoothViewModel) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.chat_round_dots_svgrepo_com),
                contentDescription = stringResource(id = R.string.app_name)
            )

            FloatingActionButton(
                onClick = {navController.navigate("devices")},
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 62.dp,end=16.dp)
            ) {
                Icon(Icons.Filled.AccountCircle, contentDescription = "Floating action button.")
            }
        }
    }
}
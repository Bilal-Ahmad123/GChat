package com.example.gchat.ui.presentation.splash

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.gchat.R
import com.example.gchat.data.bluetooth.BluetoothStateReader
import com.example.gchat.domain.bluetooth.BluetoothStateReaderImpl
import com.example.gchat.ui.presentation.viewmodels.BluetoothViewModel

@Composable
fun SplashScreen(modifier: Modifier,navController: NavController,bluetoothViewModel: BluetoothViewModel){
    val activity = (LocalContext.current as? Activity)
    val bluetoothEnabled by bluetoothViewModel.bluetoothEnabled.collectAsState(initial = false)
    val bluetoothEnableLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            navController.navigate("home")
        } else {
            activity?.finish()
        }
    }
    LaunchedEffect(Unit) {
        bluetoothViewModel.checkBluetoothEnabled()
    }

    LaunchedEffect(bluetoothEnabled) {
        if(bluetoothEnabled!!){
            navController.navigate("home")
        }
        else{
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            bluetoothEnableLauncher.launch(enableBtIntent)
        }
    }
    AnimatedLoader(modifier)
}

@Composable
fun AnimatedLoader(modifier: Modifier){
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.chat_animation
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )


    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = preloaderProgress,
        modifier = modifier
    )
}
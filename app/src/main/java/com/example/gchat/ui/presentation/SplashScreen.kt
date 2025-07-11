package com.example.gchat.ui.presentation

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.util.Log
import android.window.SplashScreen
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.gchat.R
import com.example.gchat.data.bluetooth.BluetoothStateReader
import com.example.gchat.domain.bluetooth.BluetoothStateReaderImpl

@Composable
fun SplashScreen(modifier: Modifier,navController: NavController){
    var bluetoothStateReader: BluetoothStateReader = BluetoothStateReaderImpl(LocalContext.current)
    val activity = (LocalContext.current as? Activity)
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
        if (!bluetoothStateReader.checkBluetoothEnabled()!!) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            bluetoothEnableLauncher.launch(enableBtIntent)
        }
        else{
            navController.navigate("home")
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
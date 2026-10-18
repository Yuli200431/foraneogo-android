package com.example.foraneogo.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onTiempoFinalizado: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1500L)
        onTiempoFinalizado()
    }
    SplashContent()
}

package com.example.foraneogo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import com.example.foraneogo.ui.common.TemaViewModel
import com.example.foraneogo.ui.navigation.AppNavigation
import com.example.foraneogo.ui.theme.ForaneoGOTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val temaViewModel: TemaViewModel = hiltViewModel()
            val modoOscuro by temaViewModel.modoOscuro.collectAsStateWithLifecycle()

            ForaneoGOTheme (darkTheme = modoOscuro ?: isSystemInDarkTheme())  {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}
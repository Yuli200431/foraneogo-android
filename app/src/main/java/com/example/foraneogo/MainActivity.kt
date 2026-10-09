package com.example.foraneogo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.foraneogo.ui.common.TemaViewModel
import com.example.foraneogo.ui.common.ViewModelFactory
import com.example.foraneogo.ui.navigation.AppNavigation
import com.example.foraneogo.ui.theme.ForaneoGOTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val temaViewModel: TemaViewModel = viewModel(factory = ViewModelFactory.Factory)
            val modoOscuro by temaViewModel.modoOscuro.collectAsStateWithLifecycle()

            ForaneoGOTheme (darkTheme = modoOscuro ?: isSystemInDarkTheme())  {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}
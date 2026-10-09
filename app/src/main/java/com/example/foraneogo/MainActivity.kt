package com.example.foraneogo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.foraneogo.ui.navigation.AppNavigation
import com.example.foraneogo.ui.theme.ForaneoGOTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ForaneoGOTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}
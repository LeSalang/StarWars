package com.lesa.starwars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lesa.ui.FilmsScreen
import com.lesa.uikit.theme.Purple40
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            com.lesa.uikit.theme.StarWarsTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = SCREEN_FILMS) {
                    composable(route = SCREEN_FILMS) {
                        FilmsScreen(navController)
                    }
                }
            }
        }
    }
}

const val SCREEN_FILMS = "films screen"

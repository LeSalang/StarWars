package com.lesa.starwars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lesa.ui.FilmsScreen
import com.lesa.ui.PersonsScreen
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
                    composable(route = SCREEN_PERSONS) {
                        PersonsScreen(navController)
                    }
                }
            }
        }
    }
}

const val SCREEN_FILMS = "films screen"
const val SCREEN_PERSONS = "persons screen"

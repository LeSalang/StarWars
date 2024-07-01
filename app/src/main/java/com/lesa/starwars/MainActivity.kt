package com.lesa.starwars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lesa.navigation.NavigationItem
import com.lesa.ui.FilmsScreen
import com.lesa.ui.PersonsScreen
import com.lesa.ui.PlanetScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            com.lesa.uikit.theme.StarWarsTheme(
                darkTheme = false
            ) {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = NavigationItem.Films.route) {
                    composable(NavigationItem.Films.route) {
                        FilmsScreen(navController)
                    }
                    composable(
                        route = NavigationItem.Persons.route,
                        arguments = listOf(
                            navArgument("ids") {
                                type = NavType.StringType
                            },
                            navArgument("title") {
                                type = NavType.StringType
                            }
                        )
                    ) { backStackEntry ->
                        PersonsScreen(navController = navController)
                    }
                    composable(
                        route = NavigationItem.Planet.route,
                        arguments = listOf(
                            navArgument("id") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        PlanetScreen(navController = navController)
                    }
                }
            }
        }
    }
}

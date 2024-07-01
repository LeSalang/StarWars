package com.lesa.ui

import LoadingView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lesa.features.planet.ui_logic.State
import com.lesa.ui_logic.PlanetViewModel
import com.lesa.ui_logic.models.PlanetUI
import com.lesa.uikit.theme.ErrorView

@Composable
fun PlanetScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    PlanetScreen(
        navController = navController,
        viewModel = hiltViewModel(),
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetScreen(
    navController: NavController,
    viewModel: PlanetViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val currentState = state

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = viewModel.title)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        PlanetScreenContent(
            currentState = currentState,
            modifier = modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun PlanetScreenContent(
    currentState: State,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        when (currentState) {
            State.None -> Unit
            is State.Error -> PlanetErrorView(
                state = currentState,
            )
            is State.Loading -> PlanetLoadingView(
                state = currentState,
            )
            is State.Success -> PlanetView(
                planet = currentState.planet,
            )
        }
    }
}

@Composable
private fun PlanetErrorView(
    state: State.Error,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        ErrorView(state.error.toString())
        val planet = state.planet
        if (planet != null) {
            PlanetView(
                planet = planet,
            )
        }
    }
}

@Composable
private fun PlanetLoadingView(
    state: State.Loading,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        LoadingView()
        val planet = state.planet
        if (planet != null) {
            Text(text = "Cached data:")
            PlanetView(
                planet = planet,
            )
        }
    }
}

@Composable
private fun PlanetView(
    planet: PlanetUI,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 32.dp)
    ) {
        Text(
            text = planet.name,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
        Text(
            text = planet.diameter,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
        )
        Text(
            text = planet.climate,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
        )
        Text(
            text = planet.gravity,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
        )
        Text(
            text = planet.terrain,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
        )
        Text(
            text = planet.population,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
        )
    }
}

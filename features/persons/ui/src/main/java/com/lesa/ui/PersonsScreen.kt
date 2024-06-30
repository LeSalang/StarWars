package com.lesa.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.lesa.ui_logic.PersonsViewModel
import com.lesa.ui_logic.State
import com.lesa.ui_logic.State.None.persons
import com.lesa.ui_logic.models.PersonUI

@Composable
fun PersonsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    PersonsScreen(
        navController = navController,
        viewModel = hiltViewModel(),
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonsScreen(
    navController: NavController,
    viewModel: PersonsViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val currentState = state

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Persons")
                }
            )
        }
    ) { innerPadding ->
        PersonsScreenContent(
            currentState = currentState,
            modifier = modifier.padding(innerPadding),
            navController = navController,
        )
    }
}

@Composable
private fun PersonsScreenContent(
    navController: NavController,
    currentState: State,
    modifier: Modifier = Modifier,
) {
    Column {
        when (currentState) {
            State.None -> Unit
            is State.Error -> ErrorView(
                state = currentState,
                navController = navController,
            )
            is State.Loading -> LoadingView(
                state = currentState,
                navController = navController,
            )
            is State.Success -> PersonsView(
                personUIList = currentState.persons,
                navController = navController,
            )
        }
    }
}

@Composable
private fun ErrorView(
    navController: NavController,
    state: State.Error,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = state.error.toString())
        val persons = state.persons
        if (persons != null) {
            PersonsView(
                personUIList = persons,
                navController = navController,
            )
        }
    }
}

@Composable
private fun LoadingView(
    navController: NavController,
    state: State.Loading,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        val weatherLoadingLottieComposition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                R.raw.loading_animation
            )
        )
        val loadingProgress by animateLottieCompositionAsState(
            weatherLoadingLottieComposition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
            speed = 2f
        )
        LottieAnimation(
            composition = weatherLoadingLottieComposition,
            progress = loadingProgress,
            modifier = modifier.sizeIn(10.dp, 10.dp, 100.dp, 100.dp)
        )
        val persons = state.persons
        if (persons != null) {
            Text(text = "Cached data:")
            PersonsView(
                personUIList = persons,
                navController = navController,
            )
        }
    }
}

@Composable
private fun PersonsView(
    navController: NavController,
    personUIList: List<PersonUI>?,
    modifier: Modifier = Modifier,
) {
    personUIList?.let {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            items(personUIList) { personUI ->
                FilmView(
                    person = personUI,
                    navController = navController,
                    modifier = Modifier.clickable {
                    }
                )
            }
        }
    }
}

@Composable
private fun FilmView(
    navController: NavController,
    person: PersonUI,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 32.dp)
    ) {
        Text(
            text = person.name,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
        Text(
            text = person.gender,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
        )
        Text(
            text = person.birthYear,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
        )
    }
}

package com.lesa.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.lesa.features.films.ui_logic.FilmsViewModel
import com.lesa.features.films.ui_logic.State
import com.lesa.features.films.ui_logic.models.FilmUI

@Composable
fun FilmsScreen(modifier: Modifier = Modifier) {
    FilmsScreen(modifier = modifier, viewModel = viewModel())
}

@Composable
fun FilmsScreen(
    modifier: Modifier = Modifier,
    viewModel: FilmsViewModel
) {
    val state by viewModel.state.collectAsState()
    val currentState = state
    FilmSScreenContent(currentState = currentState, modifier = modifier)
}

@Composable
fun FilmSScreenContent(
    currentState: State,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier

    ) {
        when (currentState) {
            State.None -> Unit
            is State.Error -> ErrorView(state = currentState)
            is State.Loading -> LoadingView(state = currentState)
            is State.Success -> FilmsView(filmUIList = currentState.films)
        }
    }
}

@Composable
fun ErrorView(
    state: State.Error,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Error during update")
        val films = state.films
        if (films != null) {
            FilmsView(filmUIList = films)
        }
    }
}

@Composable
fun LoadingView(
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
            isPlaying = true
        )
        LottieAnimation(
            composition = weatherLoadingLottieComposition,
            progress = loadingProgress,
            modifier = modifier.fillMaxSize()
        )
        val films = state.films
        if (films != null) {
            FilmsView(filmUIList = films)
        }
    }
}

@Composable
fun FilmsView(
    filmUIList: List<FilmUI>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        items(filmUIList) { filmUI ->
            FilmView(filmUI)
        }
    }
}

@Composable
fun FilmView(
    film: FilmUI,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = film.title,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
        Text(
            text = film.director,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
        )
        Text(
            text = film.producer,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
        )
        Text(
            text = film.releaseYear,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic,
            color = Color.Red,
        )
    }
}

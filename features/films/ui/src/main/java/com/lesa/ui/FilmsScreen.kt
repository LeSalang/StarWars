package com.lesa.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilmsScreen(
    modifier: Modifier = Modifier,
    viewModel: FilmsViewModel
) {
    val state by viewModel.state.collectAsState()
    val currentState = state

    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val searchedFilms by viewModel.searchedFilms.collectAsState()

    Scaffold(
        topBar = {
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier.fillMaxWidth()
            ) {
                SearchBar(
                    query = searchText,
                    onQueryChange = viewModel::onSearchTextChange,
                    onSearch = viewModel::onSearchTextChange,
                    active = isSearching,
                    onActiveChange = { viewModel.onToogleSearch() },
                    placeholder = { Text(text = "Search") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                    },
                    trailingIcon = {
                        if (isSearching) {
                            IconButton(
                                onClick = {
                                    viewModel.isSearching.value = false
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "exit")
                            }
                        }
                    },
                    modifier = Modifier.align(Alignment.TopCenter)
                ) {
                    FilmsView(
                        filmUIList = searchedFilms,
                        modifier = Modifier
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        FilmSScreenContent(
            currentState = currentState,
            modifier = modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun FilmSScreenContent(
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
private fun ErrorView(
    state: State.Error,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = state.error.toString())
        val films = state.films
        if (films != null) {
            FilmsView(filmUIList = films)
        }
    }
}

@Composable
private fun LoadingView(
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
        val films = state.films
        if (films != null) {
            Text(text = "Cached data:")
            FilmsView(filmUIList = films)
        }
    }
}

@Composable
private fun FilmsView(
    filmUIList: List<FilmUI>?,
    modifier: Modifier = Modifier,
) {
    filmUIList?.let {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            items(filmUIList) { filmUI ->
                FilmView(filmUI)
            }
        }
    }
}

@Composable
private fun FilmView(
    film: FilmUI,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 32.dp)
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

package com.lesa.films

import LoadingView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lesa.films.models.FilmUI
import com.lesa.navigation.NavigationItem
import com.lesa.uikit.ErrorView

@Composable
fun FilmsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    FilmsScreen(
        modifier = modifier,
        viewModel = hiltViewModel(),
        navController = navController,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilmsScreen(
    navController: NavController,
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
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                SearchBar(
                    query = searchText,
                    onQueryChange = viewModel::onSearchTextChange,
                    onSearch = viewModel::onSearchTextChange,
                    active = isSearching,
                    onActiveChange = { viewModel.onToogleSearch() },
                    placeholder = { Text(text = stringResource(id = R.string.search)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search)
                        )
                    },
                    trailingIcon = {
                        if (isSearching) {
                            IconButton(
                                onClick = {
                                    viewModel.isSearching.value = false
                                    viewModel.onSearchTextChange("")
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = stringResource(id = R.string.exit)
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth(0.8f)
                ) {
                    FilmsView(
                        filmUIList = searchedFilms,
                        modifier = Modifier,
                        navController = navController,
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        FilmsScreenContent(
            currentState = currentState,
            modifier = modifier.padding(innerPadding),
            navController = navController,
        )
    }
}

@Composable
private fun FilmsScreenContent(
    navController: NavController,
    currentState: State,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier

    ) {
        when (currentState) {
            State.None -> Unit
            is State.Error -> FilmsErrorView(
                state = currentState,
                navController = navController,
            )
            is State.Loading -> FilmsLoadingView(
                state = currentState,
                navController = navController,
            )
            is State.Success -> FilmsView(
                filmUIList = currentState.films,
                navController = navController,
            )
        }
    }
}

@Composable
private fun FilmsErrorView(
    navController: NavController,
    state: State.Error,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        ErrorView(error = stringResource(id = R.string.error, state.error.toString()))
        val films = state.films
        if (films != null) {
            FilmsView(
                filmUIList = films,
                navController = navController,
            )
        }
    }
}

@Composable
private fun FilmsLoadingView(
    navController: NavController,
    state: State.Loading,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        LoadingView()
        val films = state.films
        if (films != null) {
            Text(text = stringResource(id = R.string.cached_data))
            FilmsView(
                filmUIList = films,
                navController = navController,
            )
        }
    }
}

@Composable
private fun FilmsView(
    navController: NavController,
    filmUIList: List<FilmUI>?,
    modifier: Modifier = Modifier,
) {
    filmUIList?.let {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            items(filmUIList) { filmUI ->
                FilmView(
                    film = filmUI,
                    modifier = Modifier.clickable {
                        navController.navigate(
                            NavigationItem.Persons.createRoute(
                                ids = filmUI.characters,
                                title = filmUI.title,
                            )
                        )
                    }
                )
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
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 32.dp)
    ) {
        Text(
            text = film.title,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = stringResource(id = R.string.director, film.director),
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = stringResource(id = R.string.producer, film.producer),
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge,
            )
        Text(
            text = stringResource(id = R.string.release_year, film.releaseYear),
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic,
            color = Color.Red,
            style = MaterialTheme.typography.bodyLarge,
            )
    }
}

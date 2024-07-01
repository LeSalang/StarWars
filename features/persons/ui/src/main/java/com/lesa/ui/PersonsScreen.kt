package com.lesa.ui

import LoadingView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lesa.features.persons.ui.R
import com.lesa.features.persons.ui_logic.State
import com.lesa.features.persons.ui_logic.State.None.persons
import com.lesa.navigation.NavigationItem
import com.lesa.ui_logic.PersonsViewModel
import com.lesa.ui_logic.models.PersonUI
import com.lesa.uikit.theme.ErrorView

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
                    Text(text = viewModel.title)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(
                                id = R.string.back
                            )
                        )
                    }
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
    Column(
        modifier = modifier
    ) {
        when (currentState) {
            State.None -> Unit
            is State.Error -> PersonErrorView(
                state = currentState,
                navController = navController,
            )
            is State.Loading -> PersonLoadingView(
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
private fun PersonErrorView(
    navController: NavController,
    state: State.Error,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        ErrorView(error = stringResource(id = R.string.error, state.error.toString()))
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
private fun PersonLoadingView(
    navController: NavController,
    state: State.Loading,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        LoadingView()
        val persons = state.persons
        if (persons != null) {
            Text(text = stringResource(id = R.string.cached_data))
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
                val title = stringResource(id = R.string.homeworld_of, personUI.name)
                FilmView(
                    person = personUI,
                    modifier = Modifier.clickable {
                        navController.navigate(
                            NavigationItem.Planet.createRoute(
                                id = personUI.homeworldID,
                                title = title,
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
            text = stringResource(id = R.string.name, person.name),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
        Text(
            text = stringResource(id = R.string.gender, person.gender),
            fontWeight = FontWeight.Normal,
            color = Color.Black,
        )
        Text(
            text = stringResource(id = R.string.birth_year, person.birthYear),
            fontWeight = FontWeight.Normal,
            color = Color.Black,
        )
    }
}

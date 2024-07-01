package com.lesa.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lesa.data.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class FilmsViewModel @Inject internal constructor(
    private val getFilmsUseCaseProvider: Provider<GetFilmsUseCase>
) : ViewModel() {
    val state: StateFlow<State> =
        getFilmsUseCaseProvider
            .get()
            .invoke()
            .map { requestResult ->
                requestResult.map { filmUIList ->
                    filmUIList.sortedBy { filmUI ->
                        filmUI.episodeId
                    }
                }
            }
            .map {
                it.toState()
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, State.None)

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val searchedFilms = searchText
        .combine(state) { text, state ->
            if (text.isBlank()) {
                state.films
            } else {
                state.films?.filter { film ->
                    film.title.uppercase().contains(text.uppercase())
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }
}

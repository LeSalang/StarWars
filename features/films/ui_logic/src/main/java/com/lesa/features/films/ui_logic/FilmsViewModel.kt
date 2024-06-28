package com.lesa.features.films.ui_logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lesa.data.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class FilmsViewModel @Inject internal constructor(
    private val getFilmsUseCaseProvider: Provider<GetFilmsUseCase>
) : ViewModel() {
    public val state: StateFlow<State> =
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
}

package com.lesa.ui_logic

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lesa.features.planet.ui_logic.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class PlanetViewModel @Inject internal constructor(
    getPlanetUseCaseProvider: Provider<GetPlanetUseCase>,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private var id = savedStateHandle.get<Int>("id")
    var title = savedStateHandle.get<String>("title") ?: ""

    val state: StateFlow<State> =
        getPlanetUseCaseProvider
            .get()
            .invoke(id = id!!)
            .map { requestResult ->
                requestResult.toState()
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, State.None)
}

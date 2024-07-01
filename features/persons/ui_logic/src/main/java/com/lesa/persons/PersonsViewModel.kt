package com.lesa.persons

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lesa.persons.models.toState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class PersonsViewModel @Inject internal constructor(
    private val getPersonsUseCaseProvider: Provider<GetPersonsUseCase>,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private var personsIDs = savedStateHandle.get<String>("ids")?.split("-")?.map { it.toInt() }
    var title = savedStateHandle.get<String>("title") ?: ""

    val state: StateFlow<State> =
        getPersonsUseCaseProvider
            .get()
            .invoke(idList = personsIDs ?: emptyList())
            .map { requestResult ->
                requestResult.toState()
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, State.None)
}

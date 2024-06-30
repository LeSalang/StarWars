package com.lesa.ui_logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lesa.data.map
import com.lesa.ui_logic.models.toState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class PersonsViewModel @Inject internal constructor(
    private val getPersonsUseCaseProvider: Provider<GetPersonsUseCase>
) : ViewModel() {
    val state: StateFlow<State> =
        getPersonsUseCaseProvider
            .get()
            .invoke(listID = emptyList()) // TODO
            .map { requestResult ->
                requestResult.toState()
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, State.None)
}

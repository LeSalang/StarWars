package com.lesa.features.films.ui_logic

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class FilmsViewModel @Inject internal constructor(
    private val getFilmsUseCaseProvider: Provider<GetFilmsUseCase>
) : ViewModel() {

}

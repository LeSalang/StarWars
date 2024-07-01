package com.lesa.ui_logic.models

import com.lesa.data.RequestResult
import com.lesa.data.models.Person
import com.lesa.features.persons.ui_logic.State

internal fun Person.toPersonUI(): PersonUI {
    return PersonUI(
        name = name,
        birthYear = birthYear,
        gender = gender,
        homeworldID = homeworldID,
    )
}

internal fun RequestResult<List<PersonUI>>.toState(): State {
    return when (this) {
        is RequestResult.Error -> State.Error(persons = data, error = error)
        is RequestResult.InProgress -> State.Loading(data)
        is RequestResult.Success -> State.Success(data)
    }
}

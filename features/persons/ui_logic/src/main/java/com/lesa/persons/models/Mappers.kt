package com.lesa.persons.models

import com.lesa.data.RequestResult
import com.lesa.data.models.Person
import com.lesa.persons.State

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

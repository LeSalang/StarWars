package com.lesa.persons

import com.lesa.persons.models.PersonUI

sealed class State(open val persons: List<PersonUI>?) {

    data object None : State(persons = null)

    class Loading(persons: List<PersonUI>? = null) : State(persons)

    class Error(persons: List<PersonUI>? = null, val error: Throwable?) : State(persons)

    class Success(override val persons: List<PersonUI>) : State(persons)
}

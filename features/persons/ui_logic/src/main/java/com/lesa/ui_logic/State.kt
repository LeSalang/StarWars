package com.lesa.features.persons.ui_logic

import com.lesa.ui_logic.models.PersonUI

sealed class State(open val persons: List<PersonUI>?) {

    data object None : State(persons = null)

    class Loading(persons: List<PersonUI>? = null) : State(persons)

    class Error(persons: List<PersonUI>? = null, val error: Throwable?) : State(persons)

    class Success(override val persons: List<PersonUI>) : State(persons)
}

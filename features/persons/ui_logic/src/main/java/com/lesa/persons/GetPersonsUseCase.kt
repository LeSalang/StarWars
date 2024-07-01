package com.lesa.persons

import android.util.Log
import com.lesa.data.PersonRepository
import com.lesa.data.RequestResult
import com.lesa.data.map
import com.lesa.persons.models.PersonUI
import com.lesa.persons.models.toPersonUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetPersonsUseCase @Inject constructor(
    private val repository: PersonRepository
) {
    operator fun invoke(idList: List<Int>): Flow<RequestResult<List<PersonUI>>> {
        return if (idList.isEmpty()) {
            flowOf(RequestResult.Success(emptyList()))
        } else {
            repository.getPersons(idList = idList)
                .map { requestResult ->
                    requestResult.map { personList ->
                        Log.d("GetPersonsUseCase", "invoke: $personList")
                        personList.map { person ->
                            person.toPersonUI()
                        }
                    }
                }
        }
    }
}

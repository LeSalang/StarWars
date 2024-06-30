package com.lesa.ui_logic

import com.lesa.data.RequestResult
import com.lesa.data.SWRepository
import com.lesa.data.map
import com.lesa.ui_logic.models.PersonUI
import com.lesa.ui_logic.models.toPersonUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetPersonsUseCase @Inject constructor(
    private val repository: SWRepository
) {
    operator fun invoke(listID: List<Int>): Flow<RequestResult<List<PersonUI>>> {
        return repository.getPersons(listID = listID)
            .map { requestResult ->
                requestResult.map { personList ->
                    personList.map { person ->
                        person.toPersonUI()
                    }
                }
            }
    }
}

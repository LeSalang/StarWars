package com.lesa.data

import com.lesa.api.SWApi
import javax.inject.Inject

interface SWRepository {}

class SWRepositoryImpl @Inject constructor(
    private val api: SWApi
): SWRepository {

}

package com.lesa.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lesa.database.models.PersonDBO

@Dao
interface PersonDAO {
    @Query("SELECT * FROM persons WHERE homeworldID in (:listID)")
    suspend fun getPersons(listID: List<Int>): List<PersonDBO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersons(persons: List<PersonDBO>)
}

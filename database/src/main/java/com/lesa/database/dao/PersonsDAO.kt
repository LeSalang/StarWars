package com.lesa.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lesa.database.models.PersonDBO

@Dao
interface PersonsDAO {
    @Query("SELECT * FROM persons WHERE homeworldID in (:listID)")
    suspend fun getAll(listID: List<Int>): List<PersonDBO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(persons: List<PersonDBO>)
}

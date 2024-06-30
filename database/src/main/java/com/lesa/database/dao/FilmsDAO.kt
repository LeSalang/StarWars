package com.lesa.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lesa.database.models.FilmDBO

@Dao
interface FilmsDAO {
    @Query("SELECT * FROM films")
    suspend fun getAll(): List<FilmDBO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(films: List<FilmDBO>)
}

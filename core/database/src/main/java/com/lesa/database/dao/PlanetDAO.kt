package com.lesa.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lesa.database.models.PlanetDBO

@Dao
interface PlanetDAO {
    @Query("SELECT * FROM planets WHERE id = :id LIMIT 1")
    suspend fun getPlanet(id: Int): PlanetDBO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanet(planet: PlanetDBO)
}

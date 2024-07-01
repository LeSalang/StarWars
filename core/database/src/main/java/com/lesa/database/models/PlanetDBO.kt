package com.lesa.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planets")
data class PlanetDBO(
    @ColumnInfo("name") val name: String,
    @ColumnInfo("diameter") val diameter: String,
    @ColumnInfo("gravity") val gravity: String,
    @ColumnInfo("population") val population: String,
    @ColumnInfo("climate") val climate: String,
    @ColumnInfo("terrain") val terrain: String,
    @PrimaryKey(autoGenerate = false) val id: Int?,
)

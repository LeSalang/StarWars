package com.lesa.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons")
data class PersonDBO(
    @ColumnInfo("name") val name: String,
    @ColumnInfo("birthYear") val birthYear: String,
    @ColumnInfo("gender") val gender: String,
    @ColumnInfo("homeworldID") val homeworldID: Int?,
    @PrimaryKey(autoGenerate = false) val personID: Int?,
)

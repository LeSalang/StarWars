package com.lesa.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class FilmDBO(
    @ColumnInfo("title") val title: String,
    @PrimaryKey(autoGenerate = false) val episodeId: Int,
    @ColumnInfo("director") val director: String,
    @ColumnInfo("producer") val producer: String,
    @ColumnInfo("releaseDate") val releaseDate: Int,
    @ColumnInfo("characters") val characters: List<String>,
    @ColumnInfo("planets") val planets: List<String>,
)

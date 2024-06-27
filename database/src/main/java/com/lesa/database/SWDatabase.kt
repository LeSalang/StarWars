package com.lesa.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lesa.database.dao.FilmsDao
import com.lesa.database.models.FilmDBO
import com.lesa.database.utils.ListStringConverter

class SWDatabase internal constructor(
    private val database: SWRoomDatabase
) {
    val filmsDao: FilmsDao
        get() = database.filmsDao()
}

@Database(entities = [FilmDBO::class], version = 1)
@TypeConverters(
    ListStringConverter::class,
)
internal abstract class SWRoomDatabase : RoomDatabase() {
    abstract fun filmsDao(): FilmsDao
}

fun SWDatabase(applicationContext: Context): SWDatabase {
    val swRoomDatabase =
        Room.databaseBuilder(
            checkNotNull(applicationContext.applicationContext),
            SWRoomDatabase::class.java,
            "sw"
        ).build()
    return SWDatabase(swRoomDatabase)
}

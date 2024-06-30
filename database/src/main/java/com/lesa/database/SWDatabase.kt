package com.lesa.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lesa.database.dao.FilmsDAO
import com.lesa.database.dao.PersonsDAO
import com.lesa.database.models.FilmDBO
import com.lesa.database.models.PersonDBO
import com.lesa.database.utils.ListIntConverter
import com.lesa.database.utils.ListStringConverter

class SWDatabase internal constructor(
    private val database: SWRoomDatabase
) {
    val filmsDAO: FilmsDAO
        get() = database.filmsDAO()

    val personsDAO: PersonsDAO
        get() = database.personsDAO()
}

@Database(entities = [FilmDBO::class, PersonDBO::class], version = 1)
@TypeConverters(
    ListStringConverter::class,
    ListIntConverter::class
)
internal abstract class SWRoomDatabase : RoomDatabase() {
    abstract fun filmsDAO(): FilmsDAO
    abstract fun personsDAO(): PersonsDAO
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

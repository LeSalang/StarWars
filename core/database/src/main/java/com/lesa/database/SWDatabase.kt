package com.lesa.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lesa.database.dao.FilmDAO
import com.lesa.database.dao.PersonDAO
import com.lesa.database.dao.PlanetDAO
import com.lesa.database.models.FilmDBO
import com.lesa.database.models.PersonDBO
import com.lesa.database.models.PlanetDBO
import com.lesa.database.utils.ListIntConverter
import com.lesa.database.utils.ListStringConverter

class SWDatabase internal constructor(
    private val database: SWRoomDatabase
) {
    val filmDAO: FilmDAO
        get() = database.filmDAO()

    val personDAO: PersonDAO
        get() = database.personDAO()

    val planetDAO: PlanetDAO
        get() = database.planetDAO()
}

@Database(entities = [FilmDBO::class, PersonDBO::class, PlanetDBO::class], version = 1)
@TypeConverters(
    ListStringConverter::class,
    ListIntConverter::class
)
internal abstract class SWRoomDatabase : RoomDatabase() {
    abstract fun filmDAO(): FilmDAO
    abstract fun personDAO(): PersonDAO
    abstract fun planetDAO(): PlanetDAO
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

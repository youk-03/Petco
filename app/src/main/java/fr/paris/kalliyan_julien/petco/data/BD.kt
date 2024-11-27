package fr.paris.kalliyan_julien.petco.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [
    Activites::class,
    ActivitesAnimaux::class,
    ActivitesEspeces::class,
    ActivitesPlanifiees::class,
    Animaux::class,
    Especes::class
                      ], version = 5)
abstract class BD : RoomDatabase() {
    abstract fun ActivitesDao(): ActivitesDao
    abstract fun ActivitesAnimauxDao(): ActivitesAnimauxDao
    abstract fun ActivitesEspecesDao(): ActivitesEspecesDao
    abstract fun ActivitesPlanifieesDao(): ActivitesPlanifieesDao
    abstract fun AnimauxDao(): AnimauxDao
    abstract fun EspecesDao(): EspecesDao

    companion object {
        @Volatile
        private var instance: BD? = null

        fun getDB(c: Context): BD {
            if (instance != null) return instance!!
            instance = Room.databaseBuilder(c.applicationContext, BD::class.java, "petco")
                .fallbackToDestructiveMigration().build()
            return instance!!
        }
    }
}
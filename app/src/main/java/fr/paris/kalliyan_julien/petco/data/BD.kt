package fr.paris.kalliyan_julien.petco.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [
    Activites::class,
    ActivitesAnimaux::class,
    ActivitesEspeces::class,
    ActivitesPlanifiees::class,
    Animaux::class,
    Especes::class
                      ], version = 9)
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
            //c.deleteDatabase("petnnco") //oblige pour que ca reset aaah
            if (instance != null) return instance!!
            instance = Room.databaseBuilder(c.applicationContext, BD::class.java, "petnnco")
                .addCallback(DatabaseCallback())
                .fallbackToDestructiveMigration().build()
            Log.d("bd", instance.toString())
            return instance!!
        }

        private class DatabaseCallback : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Insertion de données initiales
                CoroutineScope(Dispatchers.IO).launch {
                    Log.d("bd", "full")
                    val especes = instance?.EspecesDao()
                    val activites = instance?.ActivitesDao()
                    val activitesEspeces = instance?.ActivitesEspecesDao()
//                    dao?.insert(MyEntity(name = "Valeur Initiale 1"))
//                    dao?.insert(MyEntity(name = "Valeur Initiale 2"))
                    especes?.insert(Especes(nom = "Chien"))
                    especes?.insert(Especes(nom = "Chat"))
                    especes?.insert(Especes(nom = "Poisson"))
                    especes?.insert(Especes(nom = "Hamster"))
                    especes?.insert(Especes(nom = "Lapin"))

                    activites?.insert(Activites(nom = "Nourrir"))
                    activites?.insert(Activites(nom = "Promener"))
                    activites?.insert(Activites(nom = "Brosser"))
                    activites?.insert(Activites(nom = "Changer l'eau"))

                    activitesEspeces?.insert(
                        ActivitesEspeces(
                            activites!!.getId("Nourrir"),
                            especes!!.getId("Chien")
                        )
                    )
                    activitesEspeces?.insert(
                        ActivitesEspeces(
                            activites!!.getId("Promener"),
                            especes!!.getId("Chien")
                        )
                    )
                    activitesEspeces?.insert(
                        ActivitesEspeces(
                            activites!!.getId("Brosser"),
                            especes!!.getId("Chien")
                        )
                    )
                    activitesEspeces?.insert(
                        ActivitesEspeces(
                            activites!!.getId("Nourrir"),
                            especes!!.getId("Chat")
                        )
                    )
                    activitesEspeces?.insert(
                        ActivitesEspeces(
                            activites!!.getId("Promener"),
                            especes!!.getId("Chat")
                        )
                    )
                    activitesEspeces?.insert(
                        ActivitesEspeces(
                            activites!!.getId("Brosser"),
                            especes!!.getId("Chat")
                        )
                    )
                    activitesEspeces?.insert(
                        ActivitesEspeces(
                            activites!!.getId("Nourrir"),
                            especes!!.getId("Poisson")
                        )
                    )
                    activitesEspeces?.insert(
                        ActivitesEspeces(
                            activites!!.getId("Changer l'eau"),
                            especes!!.getId("Poisson")
                        )
                    )
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                Log.d("bd", "onOpen called")
            }
        }
    }
}
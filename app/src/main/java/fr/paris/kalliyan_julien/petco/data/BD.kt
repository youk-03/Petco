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
                      ], version = 11)
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
                    especes?.insert(Especes(nom = "chien"))
                    especes?.insert(Especes(nom = "chat"))
                    especes?.insert(Especes(nom = "poisson"))
                    especes?.insert(Especes(nom = "hamster"))
                    especes?.insert(Especes(nom = "lapin"))

                    activites?.insert(Activites(nom = "nourrir"))
                    activites?.insert(Activites(nom = "promener"))
                    activites?.insert(Activites(nom = "brosser"))
                    activites?.insert(Activites(nom = "changer l'eau"))

                    activitesEspeces?.insert(
                        ActivitesEspeces(
                            activites!!.getId("nourrir"),
                            especes!!.getId("chien")
                        )
                    )
                    activitesEspeces?.insert(
                        ActivitesEspeces(
                            activites!!.getId("promener"),
                            especes!!.getId("chien")
                        )
                    )
                    activitesEspeces?.insert(
                        ActivitesEspeces(
                            activites!!.getId("brosser"),
                            especes!!.getId("chien")
                        )
                    )
                    activitesEspeces?.insert(
                        ActivitesEspeces(
                            activites!!.getId("nourrir"),
                            especes!!.getId("chat")
                        )
                    )
                    activitesEspeces?.insert(
                        ActivitesEspeces(
                            activites!!.getId("promener"),
                            especes!!.getId("chat")
                        )
                    )
                    activitesEspeces?.insert(
                        ActivitesEspeces(
                            activites!!.getId("brosser"),
                            especes!!.getId("Chat")
                        )
                    )
                    activitesEspeces?.insert(
                        ActivitesEspeces(
                            activites!!.getId("nourrir"),
                            especes!!.getId("poisson")
                        )
                    )
                    activitesEspeces?.insert(
                        ActivitesEspeces(
                            activites!!.getId("changer l'eau"),
                            especes!!.getId("poisson")
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
package fr.paris.kalliyan_julien.petco.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivitesAnimauxDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pays: ActivitesAnimaux)

    @Delete
    suspend fun delete(activite: ActivitesAnimaux)

    @Query("SELECT * FROM ActivitesAnimaux")
    fun loadAll(): Flow<List<ActivitesAnimaux>>

    @Query("SELECT * FROM Activites WHERE id in (SELECT activite FROM ActivitesAnimaux WHERE animal = :animal)")
     fun getActivites(animal : Int): Flow<List<Activites>>

    @Query("SELECT * FROM Animaux WHERE id in (SELECT animal FROM ActivitesAnimaux WHERE activite = :activite)")
     fun getAnimaux(activite : Int): Flow<List<Animaux>>
}
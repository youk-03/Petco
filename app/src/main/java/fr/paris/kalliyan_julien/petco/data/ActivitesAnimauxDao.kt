package fr.paris.kalliyan_julien.petco.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivitesAnimauxDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pays: ActivitesAnimaux)

    @Query("SELECT * FROM ActivitesAnimaux")
    suspend fun loadAll(): Flow<List<ActivitesAnimaux>>

    @Query("SELECT * FROM Activites WHERE id in (SELECT activite FROM ActivitesAnimaux WHERE animal = :animal)")
    suspend fun getActivites(animal : Int): Flow<List<Activites>>

    @Query("SELECT * FROM Animaux WHERE id in (SELECT animal FROM ActivitesAnimaux WHERE activite = :activite)")
    suspend fun getAnimaux(activite : Int): Flow<List<Animaux>>
}
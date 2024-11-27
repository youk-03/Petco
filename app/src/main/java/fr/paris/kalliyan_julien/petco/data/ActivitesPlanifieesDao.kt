package fr.paris.kalliyan_julien.petco.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivitesPlanifieesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pays: ActivitesPlanifiees)

    @Query("SELECT * FROM ActivitesPlanifiees")
    suspend fun loadAll(): Flow<List<ActivitesPlanifiees>>

    @Query("SELECT * FROM Activites WHERE id in (SELECT activite FROM ActivitesPlanifiees WHERE animal = :animal)")
    suspend fun getActivites(animal : Int): Flow<List<Activites>>

    @Query("SELECT * FROM Animaux WHERE id in (SELECT animal FROM ActivitesPlanifiees WHERE activite = :activite)")
    suspend fun getAnimaux(activite : Int): Flow<List<Animaux>>
}
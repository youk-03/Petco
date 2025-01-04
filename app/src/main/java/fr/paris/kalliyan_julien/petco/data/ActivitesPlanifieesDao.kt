package fr.paris.kalliyan_julien.petco.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivitesPlanifieesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pays: ActivitesPlanifiees) : Long

    @Delete
    suspend public fun delete(activites : ActivitesPlanifiees)

    //@Query("DELETE FROM ActivitesPlanifiees WHERE fin < now()") marche pas corriger (empeche la compilation)
    //suspend fun deleteEnded()

    @Query("SELECT * FROM ActivitesPlanifiees")
     fun loadAll(): Flow<List<ActivitesPlanifiees>>

//    @Query("SELECT * FROM Activites WHERE id in (SELECT activite FROM ActivitesPlanifiees WHERE animal = :animal)")
//     fun getActivites(animal : Int): Flow<List<Activites>>

    @Query("SELECT * FROM ActivitesPlanifiees WHERE animal = :animalid")
     fun getActivites(animalid : Int): Flow<List<ActivitesPlanifiees>>

    @Query("SELECT * FROM Animaux WHERE id in (SELECT animal FROM ActivitesPlanifiees WHERE activite = :activite)")
     fun getAnimaux(activite : Int): Flow<List<Animaux>>
}
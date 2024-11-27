package fr.paris.kalliyan_julien.petco.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivitesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pays: Activites)

    @Query("SELECT * FROM Activites")
    suspend fun loadAll(): Flow<List<Activites>>

    @Query("SELECT * FROM Activites Where nom LIKE :pref || '%'")
    suspend fun getPref(pref : String): Flow<List<Activites>>
}
package fr.paris.kalliyan_julien.petco.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EspecesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pays: Especes)

    @Query("SELECT * FROM Especes")
    suspend fun loadAll(): Flow<List<Especes>>

    @Query("SELECT * FROM Especes Where nom LIKE :pref || '%'")
    suspend fun getPref(pref : String): Flow<List<Especes>>
}
package fr.paris.kalliyan_julien.petco.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimauxDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(animal: Animaux) : Long //id

    @Delete
    fun delete(animal: Animaux) : Int //affected line

    @Query("SELECT * FROM Animaux")
    fun loadAll(): Flow<List<Animaux>>

    @Query("SELECT * FROM Animaux Where nom LIKE :pref || '%'")
     fun getPref(pref : String): Flow<List<Animaux>>

    @Query("SELECT id FROM Animaux Where nom LIKE :nom")
    suspend fun getId(nom : String): Int
}
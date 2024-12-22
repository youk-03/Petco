package fr.paris.kalliyan_julien.petco.data

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EspecesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(espece: Especes) : Long

    @Delete
    fun delete(espece: Especes)

    @Query("SELECT * FROM Especes")
    fun loadAll(): Flow<List<Especes>>

    @Query("SELECT * FROM Especes Where nom LIKE :pref || '%'")
    fun getPref(pref : String): Flow<List<Especes>>

    @Query("SELECT id FROM Especes Where nom LIKE :nom")
    suspend fun getId(nom : String): Int

    @Query("SELECT nom FROM Especes Where id = :resid")
    suspend fun getEspeces(resid : Int): String
}
package fr.paris.kalliyan_julien.petco.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivitesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(activites: Activites) : Long

    @Delete
    suspend fun delete(activites: Activites)

    @Query("SELECT * FROM Activites")
    fun loadAll(): Flow<List<Activites>>

    @Query("SELECT * FROM Activites Where nom LIKE :pref || '%'")
    fun getPref(pref : String): Flow<List<Activites>>

    @Query("SELECT id FROM Activites Where nom LIKE :nom")
    fun getId(nom : String): Int

    @Query("SELECT nom FROM Activites Where id = :id")
    fun getNom(id : Int): String
}
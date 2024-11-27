package fr.paris.kalliyan_julien.petco.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivitesEspecesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pays: ActivitesEspeces)

    @Query("SELECT * FROM ActivitesEspeces")
    suspend fun loadAll(): Flow<List<ActivitesEspeces>>

    @Query("SELECT * FROM Activites WHERE id in (SELECT activite FROM ActivitesEspeces WHERE espece = :espece)")
    suspend fun getActivites(espece : Int): Flow<List<Activites>>

    @Query("SELECT * FROM Especes WHERE id in (SELECT espece FROM ActivitesEspeces WHERE activite = :activite)")
    suspend fun getEspeces(activite : Int): Flow<List<Especes>>
}
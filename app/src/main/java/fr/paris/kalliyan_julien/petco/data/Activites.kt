package fr.paris.kalliyan_julien.petco.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["nom"], unique = true)])
data class Activites(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    val nom : String
)

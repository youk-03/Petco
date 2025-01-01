package fr.paris.kalliyan_julien.petco.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["nom"], unique = true)])
data class Especes(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val nom : String,
)

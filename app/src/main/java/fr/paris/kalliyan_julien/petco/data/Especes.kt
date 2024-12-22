package fr.paris.kalliyan_julien.petco.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Especes(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val nom : String,
)

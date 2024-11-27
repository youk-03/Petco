package fr.paris.kalliyan_julien.petco.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Activites(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val nom : String
)

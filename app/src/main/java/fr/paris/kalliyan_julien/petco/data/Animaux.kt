package fr.paris.kalliyan_julien.petco.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
    ForeignKey(entity = Especes::class,
        parentColumns = ["id"],
        childColumns = ["espece"],
        onDelete = CASCADE)]
)
data class Animaux(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val nom : String,
    val img : String,
    val espece : Int
)

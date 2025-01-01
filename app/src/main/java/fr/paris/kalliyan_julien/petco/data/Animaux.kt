package fr.paris.kalliyan_julien.petco.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
    ForeignKey(entity = Especes::class,
        parentColumns = ["id"],
        childColumns = ["espece"],
        onDelete = CASCADE)],
    indices = [Index(value = ["nom"], unique = true)]
)
data class Animaux(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    var nom : String,
    var iconName : String?,
    var iconPath : String?,
    val espece : Int
)

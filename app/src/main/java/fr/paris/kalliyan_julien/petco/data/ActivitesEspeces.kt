package fr.paris.kalliyan_julien.petco.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    primaryKeys = ["activite","espece"],
    foreignKeys = [
        ForeignKey(entity = Activites::class,
            parentColumns = ["id"],
            childColumns = ["activite"],
            onDelete = CASCADE
        ),
        ForeignKey(entity = Especes::class,
            parentColumns = ["id"],
            childColumns = ["espece"],
            onDelete = CASCADE
        )
    ]
)
data class ActivitesEspeces(
    val activite : Int,
    val espece : Int
)

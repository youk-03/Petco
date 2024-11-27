package fr.paris.kalliyan_julien.petco.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(
    primaryKeys = ["activite","animal"],
    foreignKeys = [
        ForeignKey(entity = Activites::class,
            parentColumns = ["id"],
            childColumns = ["activite"],
            onDelete = CASCADE
        ),
        ForeignKey(entity = Animaux::class,
            parentColumns = ["id"],
            childColumns = ["animal"],
            onDelete = CASCADE
        )
    ]
)
data class ActivitesAnimaux(
    val activite : Int,
    val animal : Int
)

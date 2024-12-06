package fr.paris.kalliyan_julien.petco.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(
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
data class ActivitesPlanifiees(
    @PrimaryKey(autoGenerate = true) var id : Int = 0,
    val activite : Int,
    val animal : Int,
    val debut : Long, /*stocker temps en seconde puis convertir*/
    val fin : Long,
    val repeat : Int
)

package fr.paris.kalliyan_julien.petco.data

import android.app.PendingIntent
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.sql.SQLTimeoutException
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
    var date : Long, /*stocker temps en milli seconde puis convertir*/
    var note : String,
    var repeat : Int /* 0: unique, 1: dayly, 2: weekly, 3: monthly */
)

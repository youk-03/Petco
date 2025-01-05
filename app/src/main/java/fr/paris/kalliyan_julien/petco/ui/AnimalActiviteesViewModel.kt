package fr.paris.kalliyan_julien.petco.ui

import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.wear.compose.foundation.rememberActiveFocusRequester
import fr.paris.kalliyan_julien.petco.data.Activites
import fr.paris.kalliyan_julien.petco.data.ActivitesPlanifiees
import fr.paris.kalliyan_julien.petco.data.Animaux
import fr.paris.kalliyan_julien.petco.data.BD
import fr.paris.kalliyan_julien.petco.scheduleNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

public class AnimalActiviteesViewModel(application: Application)  : AndroidViewModel(application){

    val hebdo =  mutableStateOf(false)
    val daily =  mutableStateOf(false)
    val unique =  mutableStateOf(false)


    val current_activite = mutableStateOf(Activites(id = -1, nom = "test"))
    val notes = mutableStateOf("")

    private val dao by lazy { BD.getDB(application) }
    val activitesdao = dao.ActivitesDao()
    val animauxdao = dao.AnimauxDao()
    val activitesPlanifieesdao = dao.ActivitesPlanifieesDao()

    var allActivitesFlow = activitesdao.loadAll()
    var allActivitesPlanifieesFlow = activitesPlanifieesdao.loadAll()

    var isDialogOpen = mutableStateOf(false)
    var add_activity = mutableStateOf("")

    var calendar = mutableStateOf(Calendar.getInstance())


    /////////////////////////////////////////////////////////////////////////////////:
    var allAnimauxFlow = animauxdao.loadAll()

    var current_animal = mutableStateOf(Animaux(-1,"","","",-1)) //animal courant (après avoir cliqué sur l'animal)

    var isDialogOpenModif = mutableStateOf(false)

    val adding = mutableStateOf(false)
    /////////////////////////////////////////////////////////////////////////////////


    fun addActivite(activites: String){
        viewModelScope.launch(Dispatchers.IO) {
            val id = activitesdao.insert(Activites(nom = activites.lowercase()))
            if(id < 0){
                //echec
                Log.d("bd", "insertion erreur addActivite")
            }
        }
        isDialogOpen.value = false
    }

    fun updateAnimal(animal: Animaux){
        viewModelScope.launch {
            val rowsUpdated = animauxdao.updateAnimal(animal)
            if (rowsUpdated <= 0) {
                Log.d("bd", "modification erreur updateAnimal")
            }
        }
        isDialogOpenModif.value = false
    }

    fun addActivitesPlanifiees(notes: String, context: Context){
        val  time= calendar.value.timeInMillis

        var repeatCode = 0
        if(daily.value){
            repeatCode= 1
        }
        else if (hebdo.value){
            repeatCode= 2
        }

        val intent= scheduleNotification(current_activite.value.nom,notes,current_animal.value.nom,time,repeatCode,context)

        insertActivitesPlanifiees(current_activite.value.id,current_animal.value.id,time,notes,repeatCode)
        adding.value=false
    }

    private  fun insertActivitesPlanifiees(activite : Int, animal: Int, date: Long, notes: String, repeat: Int=0){
        viewModelScope.launch(Dispatchers.IO) {
            val id= activitesPlanifieesdao.insert(ActivitesPlanifiees(
                activite= activite,
                animal= animal,
                date= date,
                note= notes,
                repeat = repeat
            ))
            if(id < 0){
                //echec
                Log.d("bd", "insertion erreur addActivitesPlanifiees")
            }
        }
    }

}

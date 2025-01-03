package fr.paris.kalliyan_julien.petco.ui

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fr.paris.kalliyan_julien.petco.data.ActivitesPlanifiees
import fr.paris.kalliyan_julien.petco.data.BD
import fr.paris.kalliyan_julien.petco.deleteNotif
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Calendar

class ActivitesPlanifieesViewModel(application: Application)  : AndroidViewModel(application){
    private val dao by lazy { BD.getDB(application) }
    val activitesPlanifieesdao = dao.ActivitesPlanifieesDao()
    val activites = dao.ActivitesDao()
    val animaux = dao.AnimauxDao()

    var allActivitesPlanifiees = activitesPlanifieesdao.loadAll()

    var isSelected = mutableStateOf(false)
    var selectedActivitesPlanifiees =  mutableStateOf(ActivitesPlanifiees(-1,0,0,0,"",0))

    fun getNomActivite(activiteID : Int) : String{
        var nom =""
        runBlocking { // this: CoroutineScope
            launch {
                nom= activites.getNom(activiteID)
            }
        }
        return nom
    }

    fun getNomAnimal(animalID : Int) : String{
        var nom =""
        runBlocking { // this: CoroutineScope
            launch {
                nom= animaux.getNom(animalID)
            }
        }
        return nom
    }

    fun delete(activitesPlanifiees: ActivitesPlanifiees,context: Context){
        if(!isPassed(activitesPlanifiees)){
            runBlocking { // this: CoroutineScope
                launch {
                    deleteNotif(getNomActivite(activitesPlanifiees.activite),getNomAnimal(activitesPlanifiees.animal),activitesPlanifiees,context)
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            activitesPlanifieesdao.delete(activitesPlanifiees)
        }
    }

    fun isPassed(activites: ActivitesPlanifiees) : Boolean{
        val current= Calendar.getInstance()
        current.get(Calendar.YEAR)
        current.get(Calendar.MONTH)
        current.get(Calendar.DAY_OF_MONTH)
        current.get(Calendar.HOUR_OF_DAY)
        current.get(Calendar.MINUTE)

        return activites.date < current.timeInMillis
    }

    fun select(activites: ActivitesPlanifiees){
        isSelected.value= !isSelected.value
        selectedActivitesPlanifiees.value= activites
    }
}
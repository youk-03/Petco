package fr.paris.kalliyan_julien.petco.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import fr.paris.kalliyan_julien.petco.data.BD

public class AnimalActiviteesViewModel(application: Application)  : AndroidViewModel(application){

    val hebdo =  mutableStateOf(false)
    val daily =  mutableStateOf(false)
    val unique =  mutableStateOf(false)

    val activites = mutableStateOf("")
    val notes = mutableStateOf("")

    private val dao by lazy { BD.getDB(application) }
    val activitesdao = dao.ActivitesDao()
    val animauxdao = dao.AnimauxDao()
    val activitesPlanifieesdao = dao.ActivitesPlanifieesDao()

    var allActivitesFlow = activitesdao.loadAll()
    var allActivitesPlanifieesFlow = activitesPlanifieesdao.loadAll()


}

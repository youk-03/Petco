package fr.paris.kalliyan_julien.petco.ui

import android.app.Application
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fr.paris.kalliyan_julien.petco.R
import fr.paris.kalliyan_julien.petco.data.Animaux
import fr.paris.kalliyan_julien.petco.data.BD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AnimalEspeceViewModel(application: Application) : AndroidViewModel(application)  {

        private val dao by lazy { BD.getDB(application) }
        val especesdao = dao.EspecesDao()
        val animauxdao = dao.AnimauxDao()

        var allEspeceFlow = especesdao.loadAll()//.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        //var allAnimauxFlow = animauxdao.loadAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        var name = mutableStateOf("")
        //var espece = mutableStateOf(allEspeceFlow.value[0].nom)
        //var especetmp = mutableStateOf(allEspeceFlow.value[0])
        var icone = mutableIntStateOf(0)

        val animals = listOf(
                "Naya" to R.drawable.naya,
                "Gibs" to R.drawable.chat,
                "chat_noir" to R.drawable.chatn,
                "dog" to R.drawable.dog,
                "hamser" to R.drawable.hamster,
                "fish" to R.drawable.fish,
                "bunny" to R.drawable.lapin
        )

        var selectedIconIndex = mutableIntStateOf(-1)





        fun addAnimal(name: String, espece: Int, icone: String){
                //parcourir la liste all espece flow pour avoir l'id correspondant au string
                viewModelScope.launch(Dispatchers.IO) {
                        animauxdao.insert(Animaux(nom=name, img = icone, espece = espece))
                }
        }

}
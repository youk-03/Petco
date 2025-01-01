package fr.paris.kalliyan_julien.petco.ui

import android.app.Application
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fr.paris.kalliyan_julien.petco.R
import fr.paris.kalliyan_julien.petco.data.Animaux
import fr.paris.kalliyan_julien.petco.data.BD
import fr.paris.kalliyan_julien.petco.data.Especes
import fr.paris.kalliyan_julien.petco.screen.AnimalCard
import fr.paris.kalliyan_julien.petco.screen.SlidingCard
import fr.paris.kalliyan_julien.petco.ui.theme.animals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.compose.runtime.State

class AnimalEspeceViewModel(application: Application) : AndroidViewModel(application)  {

        private val dao by lazy { BD.getDB(application) }
        val especesdao = dao.EspecesDao()
        val animauxdao = dao.AnimauxDao()

        var allEspeceFlow = especesdao.loadAll()
        var allAnimauxFlow = animauxdao.loadAll()

        var add = mutableStateOf(false)

        var name = mutableStateOf("")
        var espece = mutableStateOf(Especes(-1,""))

        var selectedIconIndex = mutableIntStateOf(-1)
        var iconPath = mutableStateOf("")

        private val _especeName = mutableStateOf("")
        val especename: State<String> = _especeName

        var add_espece = mutableStateOf("")
        var isDialogOpen = mutableStateOf(false)



        fun addAnimal(name: String, espece: Int, img: String?, iconPath : String?){
                viewModelScope.launch(Dispatchers.IO) {
                        val id = animauxdao.insert(Animaux(nom=name.lowercase(), iconName = img, iconPath = iconPath, espece = espece))
                        if(id < 0){
                                //echec
                                Log.d("bd", "insertion erreur addAnimal")
                        }
                }
                add.value = false
        }

        fun addEspece(espece: String){
                viewModelScope.launch(Dispatchers.IO) {
                        val id = especesdao.insert(Especes(nom = espece.lowercase()))
                        if(id < 0){
                                //echec
                                Log.d("bd", "insertion erreur addEspece")
                        }
                }
                isDialogOpen.value = false
        }

        fun deleteAnimal(animal : Animaux){
                viewModelScope.launch(Dispatchers.IO) {
                       val row = animauxdao.delete(animal)
                        if(row > 0) {
                                //echec
                                Log.d("bd", "supression erreur deleteAnimal")
                        }
                }
        }



        fun getEspeceFromDB(espece : Int) {
                viewModelScope.launch {
                       try{
                               _especeName.value =  especesdao.getEspeces(espece)
                       } catch (e : Exception) {
                               Log.e("AnimalEspeceViewModel", "Error getting string espece from ID")
                               _especeName.value = "Error: ${e.message}"
                       }
                 }
        }



}
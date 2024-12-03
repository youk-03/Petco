package fr.paris.kalliyan_julien.petco.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import fr.paris.kalliyan_julien.petco.data.BD

public class AnimalActiviteesViewModel(application: Application)  : AndroidViewModel(application){

    private val dao by lazy { BD.getDB(application) }


}

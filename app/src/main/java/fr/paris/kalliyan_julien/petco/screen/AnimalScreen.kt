package fr.paris.kalliyan_julien.petco.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fr.paris.kalliyan_julien.petco.ui.MainViewModel

@Composable
fun AnimalScreen(model : MainViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Row {
            Text("vos prochaines activitées: ")
            //possibilité de cliquer sur une activité / gerer / supprimer etc ordonnées en temps
        }
        Row{
            model.ShowListActivity()
        }


    }
}
package fr.paris.kalliyan_julien.petco.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import fr.paris.kalliyan_julien.petco.ui.AnimalActiviteesViewModel
import fr.paris.kalliyan_julien.petco.ui.MainViewModel

@Composable
fun AnimalScreen(animalActivitesModel : AnimalActiviteesViewModel, mainmodel : MainViewModel, navController: NavController) {
    val animal by animalActivitesModel.current_animal
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ){
        Row{
            Text(animal.nom)
        }
        Row{
            //Text(animal.espece) obligé de faire une requête ici pour récupérer la bonne espece
        }
        Row {
          //liste des activitees de l'animal
        }
        Row{
            Button(onClick = {mainmodel.navigateTo(navController,"add_activites", false)}) { Text("Ajouter une activité") }
        }
    }
}
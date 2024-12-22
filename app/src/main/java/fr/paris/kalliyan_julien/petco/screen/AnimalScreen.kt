package fr.paris.kalliyan_julien.petco.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.paris.kalliyan_julien.petco.navigateTo
import fr.paris.kalliyan_julien.petco.ui.AnimalActiviteesViewModel
import fr.paris.kalliyan_julien.petco.ui.AnimalEspeceViewModel
import fr.paris.kalliyan_julien.petco.ui.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalScreen(animalActivitesModel : AnimalActiviteesViewModel, animalEspeceViewModel: AnimalEspeceViewModel,  mainmodel : MainViewModel, navController: NavController) {
    val animal by animalActivitesModel.current_animal
    val espece by animalEspeceViewModel.especename
    var onDelete by remember {mutableStateOf(false)}
    //ajouter un bouton pour supprimer l'animal et avant un alert dialog pour demander vous êtes sur ?

    if(onDelete){
        BasicAlertDialog(
            onDismissRequest = {onDelete = false},
            modifier = Modifier.padding(20.dp).background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp)),
            content = {
                Column (modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround) {
                    Row{
                        Text("Êtes vous sûr(e) de vouloir supprimer ${animal.nom} :(")
                    }
                    Row{
                        Button(modifier = Modifier.padding(10.dp),onClick = {
                            animalEspeceViewModel.deleteAnimal(animal)
                            navigateTo(navController, "home", true)
                        }){Text("Oui")}
                        Button(modifier = Modifier.padding(10.dp),onClick = {onDelete = false}){Text("Non")}
                    }
                }
            }
        )
    }

    LaunchedEffect(animal.espece) {
        animalEspeceViewModel.getEspeceFromDB(animal.espece)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ){
        Row{
            Button(onClick = {onDelete = true}) { Text("Supprimer") }
        }
        Row{
            AnimalIcon(animal.iconName, animal.iconPath) //afficher l'image en carré avec bord arrondi et plus gros plutot
            Text(animal.nom, modifier = Modifier.padding(20.dp))
        }
        Row{
            Text(espece, modifier = Modifier.padding(20.dp))
        }
        Row {
          //liste des activitees de l'animal
        }
        Row{
            Button(onClick = {navigateTo(navController,"add_activites", false)}) { Text("Ajouter une activité") }
        }
    }
}
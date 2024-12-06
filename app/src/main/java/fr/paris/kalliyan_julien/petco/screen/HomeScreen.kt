package fr.paris.kalliyan_julien.petco.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fr.paris.kalliyan_julien.petco.ui.MainViewModel


@Composable
fun HomeScreen(navController: NavHostController, model : MainViewModel) {
    //ajouter la prochaine activité qui devrait avoir lieu

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row (modifier = Modifier.padding(20.dp)){
            Text(model.message[model.i])
        }

        Row(modifier = Modifier.weight(1f)) {

            model.ShowlistAnimal()

        }

        Row(modifier = Modifier.padding(20.dp)) {
            Button(onClick = {model.navigateTo(navController,"add_pet", false)}) { Text("Ajouter un compagnon") }
        }
    }
}
package fr.paris.kalliyan_julien.petco.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import fr.paris.kalliyan_julien.petco.ui.AnimalActiviteesViewModel
import fr.paris.kalliyan_julien.petco.ui.MainViewModel
import androidx.compose.material3.Button

@Composable
fun ActivitesScreen(model : AnimalActiviteesViewModel, navController: NavController, mainmodel : MainViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Row {
            Text("vos prochaines activitées: ")
            //possibilité de cliquer sur une activité / gerer / supprimer etc ordonnées en temps
        }
//        Row{
//            //model.ShowListActivity()
//        }


    }
}

@Composable
fun ShowListActivity(){//decommenter et changer la var message pour une var qui contient la liste des prochaines activites (juste l'activités)
//    Box(
//        modifier = Modifier
//            .padding(16.dp)
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(16.dp))
//            .background(Color(0xFF590606))
//            .border(
//                width = 2.dp,
//                color = Color(0xFF341706),
//                shape = RoundedCornerShape(16.dp)
//            ),
//        contentAlignment = Alignment.Center
//    ) {
//        LazyColumn(Modifier.wrapContentHeight().padding(16.dp)) {
//            items(message) {//ici liste des activites à suivre
//                SlidingCard (toShow = {
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 8.dp)
//                            .clickable(onClick = {}),
//                        shape = RoundedCornerShape(8.dp)
//                    ) {Text(it)}
//                })
//            }
//        }
//    }
}
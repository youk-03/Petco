package fr.paris.kalliyan_julien.petco.screen

import android.app.AlertDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import fr.paris.kalliyan_julien.petco.ui.AnimalActiviteesViewModel
import fr.paris.kalliyan_julien.petco.ui.MainViewModel
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fr.paris.kalliyan_julien.petco.data.Activites
import fr.paris.kalliyan_julien.petco.data.ActivitesPlanifiees
import fr.paris.kalliyan_julien.petco.data.Animaux
import fr.paris.kalliyan_julien.petco.navigateTo
import fr.paris.kalliyan_julien.petco.ui.ActivitesPlanifieesViewModel
import java.util.Calendar
import java.util.Date

@Composable
fun ActivitesScreen(model : ActivitesPlanifieesViewModel, navController: NavController, mainModel : MainViewModel) {
    val listActivites by model.allActivitesPlanifiees.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Row (modifier = Modifier.padding(20.dp)){
            Text("vos activitées planifiées: ")
            //possibilité de cliquer sur une activité / gerer / supprimer etc ordonnées en temps
        }

        Row(modifier = Modifier.weight(1f)) {
            ShowListActivity(listActivites,model)
        }
    }
}

@Composable
fun ShowListActivity(list : List<ActivitesPlanifiees>,model : ActivitesPlanifieesViewModel){
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(16.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if(list.isEmpty()){
            Text("Aucune activité programmée", modifier = Modifier.padding(20.dp).fillMaxWidth().align(Alignment.Center))
        }
        else {
            LazyColumn(Modifier.wrapContentHeight().padding(16.dp)) {
                items(list.sortedWith(compareBy{it.date})) {
                    SlidingCard(toShow = {
                        ActiviteCard(it, onClick = { model.select(it) }, model)
                    }, action ={
                        model.delete(it,context)
                    } )
                }
            }
        }


    }
}

@Composable
fun ActiviteCard(activite: ActivitesPlanifiees, onClick : () -> Unit,model : ActivitesPlanifieesViewModel) {
    var textColor = Color.Unspecified
    if(model.isPassed(activite)){
        textColor=MaterialTheme.colorScheme.tertiary
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Date de l'activite
            val date = Date(activite.date)
            val dateFormat = android.text.format.DateFormat.getLongDateFormat(LocalContext.current)
            val timeFormat = android.text.format.DateFormat.getTimeFormat(LocalContext.current)

            Text(
                text= dateFormat.format(date) + " " + timeFormat.format(date),
                color= textColor
            )

            Spacer(modifier = Modifier.width(16.dp)) // Espace

            // Nom de l'animal
            Text(
                text = model.getNomAnimal(activite.animal)+": "+model.getNomActivite(activite.activite),
                color= textColor
            )

            Spacer(modifier = Modifier.width(16.dp)) // Espace

            // Repetition
            var repetitionTewt= ""
            when(activite.repeat) {
                1 -> repetitionTewt= "D"
                2 -> repetitionTewt= "W"
                3 -> repetitionTewt= "M"
            }
            Text(
                text = repetitionTewt,
                color= textColor
            )
        }
    }
}
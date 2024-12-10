package fr.paris.kalliyan_julien.petco.screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fr.paris.kalliyan_julien.petco.data.Animaux
import fr.paris.kalliyan_julien.petco.ui.AnimalActiviteesViewModel
import fr.paris.kalliyan_julien.petco.ui.AnimalEspeceViewModel
import fr.paris.kalliyan_julien.petco.ui.MainViewModel
import fr.paris.kalliyan_julien.petco.ui.theme.animals


@Composable
fun HomeScreen(navController: NavHostController, model : MainViewModel, animalActivitesViewModel: AnimalActiviteesViewModel) {
    //ajouter la prochaine activité qui devrait avoir lieu
    val allanimal by animalActivitesViewModel.allAnimauxFlow.collectAsState(emptyList())

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row (modifier = Modifier.padding(20.dp)){
            Text(model.message[model.i])
        }

        Row(modifier = Modifier.weight(1f)) {

            ShowlistAnimal(allanimal, animalActivitesViewModel, model, navController)

        }

        Row(modifier = Modifier.padding(20.dp)) {
            Button(onClick = {model.navigateTo(navController,"add_pet", false)}) { Text("Ajouter un compagnon") }
        }
    }
}

@Composable
fun ShowlistAnimal(list : List<Animaux>, animalActivitesViewModel: AnimalActiviteesViewModel, model : MainViewModel, navController: NavHostController){
    var img = 0
    Box(
        modifier = Modifier
            .padding(16.dp) // Espace autour
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)) // Coupe les coins pour les arrondir
            .background(Color(0xFF590606)) // Ajoute un fond
            .border(
                width = 2.dp,
                color = Color(0xFF341706), // Couleur de la bordure
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(Modifier.wrapContentHeight().padding(16.dp)) {
            items(list) {
                for(p in animals){//ça le rend lent trouver une autre manière de faire !
                    if(p.first == it.img){
                        img = p.second
                        break
                    }
                }
                SlidingCard (toShow = {AnimalCard(it,
                    img,
                    onClick = {
                    animalActivitesViewModel.current_animal.value = it
                        model.navigateTo(navController,"animal",false)
                    }
                ) })
            }
        }
    }
}

@Composable
fun AnimalCard(animal: Animaux, image: Int, onClick : () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp) // Espacement interne de la carte
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically // Aligne les éléments verticalement
        ) {
            // Image de l'animal
            Image(
                painter = painterResource(image),
                contentDescription = "Animal Image",
                modifier = Modifier
                    .size(64.dp) // Taille de l'image
                    .clip(CircleShape) // Image ronde
                    .border(2.dp, Color.Gray, CircleShape), // Bordure autour de l'image
                contentScale = ContentScale.Crop // Recadre l'image
            )

            Spacer(modifier = Modifier.width(16.dp)) // Espace entre l'image et le texte

            // Nom de l'animal
            Text(
                text = animal.nom,
            )
        }
    }
}

@Composable
fun SlidingCard(toShow : @Composable () -> Unit) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            false
        }
    )
    androidx.compose.material3.SwipeToDismissBox(
        state = dismissState,
        enableDismissFromEndToStart = false,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp)
                    .background(Color.Transparent)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(16.dp),
                    tint = Color.White
                )
            }
        }
    ) {
        toShow()

    }
}



package fr.paris.kalliyan_julien.petco.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import fr.paris.kalliyan_julien.petco.data.Animaux
import fr.paris.kalliyan_julien.petco.navigateTo
import fr.paris.kalliyan_julien.petco.ui.AnimalActiviteesViewModel
import fr.paris.kalliyan_julien.petco.ui.AnimalEspeceViewModel
import fr.paris.kalliyan_julien.petco.ui.MainViewModel
import fr.paris.kalliyan_julien.petco.ui.theme.animals



@Composable
fun HomeScreen(navController: NavHostController, model : MainViewModel, animalActivitesViewModel: AnimalActiviteesViewModel) {
    //ajouter la prochaine activité qui devrait avoir lieu
    val allanimal by animalActivitesViewModel.allAnimauxFlow.collectAsState(emptyList())

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item{
            Text(model.message[model.i], modifier = Modifier.padding(20.dp))
        }

        item {

            ShowlistAnimal(allanimal, animalActivitesViewModel, model, navController)

        }

        item {
            Button(onClick = {navigateTo(navController,"add_pet", false)},modifier = Modifier.padding(20.dp)) { Text("Ajouter un compagnon") }
        }
    }
}

@Composable
fun ShowlistAnimal(list : List<Animaux>, animalActivitesViewModel: AnimalActiviteesViewModel, model : MainViewModel, navController: NavHostController){
    Box(
        modifier = Modifier
            .padding(16.dp)
            .height(250.dp)
            .fillMaxWidth()
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
            Text("c'est bien vide ici :( créez un nouveau compagnon", modifier = Modifier.padding(20.dp).fillMaxWidth().align(Alignment.Center))
        }
        else {
            LazyColumn(Modifier.wrapContentHeight().padding(16.dp)) {
                items(list) {
                        AnimalCard(it, it.iconPath,
                            it.iconName,
                            onClick = {
                                animalActivitesViewModel.current_animal.value = it
                                navigateTo(navController, "animal", false)
                            }
                        )
                }
            }
        }


    }
}

@Composable
fun AnimalCard(animal: Animaux, iconPath: String?,image : String?, onClick : () -> Unit) {
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

            AnimalIcon(image, iconPath)

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = animal.nom,
            )
        }
    }
}

@Composable
fun AnimalIcon(iconName: String?, customIconPath: String?) {
    if (customIconPath != null) {
        AsyncImage(
            model = customIconPath,
            contentDescription = "Custom Icon",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.onPrimary, CircleShape),
            contentScale = ContentScale.Crop
        )
    } else if (iconName != null) {

        val context = LocalContext.current
        val resId = remember(iconName) {
            animals[iconName]
        }
        Image(
            painter = painterResource(id = resId!!),
            contentDescription = "Default Icon",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun SlidingCard(toShow : @Composable () -> Unit, action: () -> Unit ) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.Settled) {
                Log.d("swipe", "swiped")
                action()

                true
            } else {
                false
            }
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



package fr.paris.kalliyan_julien.petco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import fr.paris.kalliyan_julien.petco.ui.theme.PetCoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetCoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainPage(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}



@Composable
fun MainPage(name: String, modifier: Modifier = Modifier, model: MainViewModel = viewModel()) {
    val context = LocalContext.current

    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { model.MainTopBar() },
        bottomBar = { model.MainBottomBar(navController) },
        snackbarHost = { SnackbarHost(snackbarHostState) }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home", enterTransition = { slideInHorizontally() }, exitTransition = { slideOutHorizontally()}) { HomeScreen(navController,model) }
            composable("pictures", enterTransition = { slideInHorizontally() }, exitTransition = { slideOutHorizontally() }) { PicScreen() }
            composable("animals", enterTransition = { slideInHorizontally() }, exitTransition = { slideOutHorizontally() }) { AnimalScreen(model) }
            composable("settings", enterTransition = { slideInHorizontally() }, exitTransition = { slideOutHorizontally() }) { SettingsScreen() }
            composable("add_pet", enterTransition = { fadeIn() }, exitTransition = { fadeOut() }) { AddpetScreen(model) }
        }
    }
}


//pages

@Composable
fun HomeScreen(navController: NavHostController, model : MainViewModel) {
    //ajouter la prochaine activité qui devrait avoir lieu

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row {
            Text(model.message[model.i])
        }

        model.ShowlistAnimal()

        Row {
            Button(onClick = {model.navigateTo(navController,"add_pet", false)}) { Text("Ajouter un compagnon") }
        }
    }
}

@Composable
fun PicScreen(modifier: Modifier = Modifier) {
    Text("pic")
}

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Text("settings")
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddpetScreen(model : MainViewModel){

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        //nom
        //espece parmis un choix d'espece ou une nouvelle
        //photo parmis choix ou une nouvelle

        Row{
            Text("nom de votre nouveaux compagnon: ")
        }

        Row {

            OutlinedTextField(value = model.name.value, onValueChange = {model.name.value = it}, label = { Text("nom")} )

        }
        Row {

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {expanded = ! expanded}
            ) {
                TextField(
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    value = model.espece.value,
                    onValueChange = {model.espece.value = it},
                    label = {Text("Espèces")},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false}
                ) {
                    model.especes.forEach{ c->
                        DropdownMenuItem(
                            onClick = {
                                model.espece.value = c
                                expanded = false },
                            text = {Text(text = c)}
                        )

                    }
                }
            }


        }
        Row {
            Text("Choississez une icone ou prenez une photo de votre compagnon : ")
        }

        Row{
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3), // Grille de 3 colonnes
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(model.animals.size) { index ->
                        val isSelected = (index == model.selectedIconIndex.intValue)
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Color.Blue else Color.White)
                                .clickable {
                                    model.selectedIconIndex.value = index // Met à jour l'icône sélectionnée
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = model.animals[index].second),
                                contentDescription = "Icon $index",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }
            }
        }

        Row{
            Button(modifier = Modifier.padding(10.dp), onClick = {}){model.CameraIcon()}
            Button(modifier = Modifier.padding(10.dp),onClick = {}){Text("valider")}
        }

    }

}

//pages

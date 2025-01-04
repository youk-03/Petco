package fr.paris.kalliyan_julien.petco.screen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import fr.paris.kalliyan_julien.petco.navigateTo
import fr.paris.kalliyan_julien.petco.ui.ActivitesPlanifieesViewModel
import fr.paris.kalliyan_julien.petco.ui.AnimalActiviteesViewModel
import fr.paris.kalliyan_julien.petco.ui.AnimalEspeceViewModel
import fr.paris.kalliyan_julien.petco.ui.MainViewModel
import fr.paris.kalliyan_julien.petco.ui.theme.CameraIcon
import fr.paris.kalliyan_julien.petco.ui.theme.animals
import fr.paris.kalliyan_julien.petco.ui.theme.copyImageToAppDirectory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalScreen(animalActivitesModel : AnimalActiviteesViewModel, animalEspeceViewModel: AnimalEspeceViewModel, activitesPlanifieesViewModel : ActivitesPlanifieesViewModel, mainmodel : MainViewModel, navController: NavController) {
    var animal by animalActivitesModel.current_animal
    val espece by animalEspeceViewModel.especename
    var onDelete by remember {mutableStateOf(false)}
    var isDialogOpenModif by animalActivitesModel.isDialogOpenModif

    activitesPlanifieesViewModel.onId_animalChange(animal.id)

    val listActivites by activitesPlanifieesViewModel.animalActivitesPlanifiees.collectAsState(initial = emptyList())

    val context = LocalContext.current


    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                val imagePath = copyImageToAppDirectory(context, it)
                if(imagePath != null) {
                    animal.iconPath = imagePath
                    animal.iconName = null
                } else {
                    if(animal.iconName == null)
                        animal.iconPath = null
                    Log.e("GalleryLauch", "failed saving image")
                }
            }
        }
    )

    //modification de l'animal
    //laisser la possibilité de ne modifier que le nom et l'icone en recuperant une photo dans la galerie
    if(isDialogOpenModif){
        BasicAlertDialog(
            onDismissRequest = {isDialogOpenModif = false},
            modifier = Modifier.padding(20.dp).background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp)),
            content = {
                Column (modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Row{
                        OutlinedTextField(modifier = Modifier.fillMaxWidth(),value = animal.nom, onValueChange = { animal = animal.copy(nom = it)}, label = { Text("nouveau nom") }, singleLine = true)
                    }
                    Row{
                        Text("changer l'icone :")
                        OutlinedIconButton(modifier = Modifier.padding(10.dp), onClick = {  galleryLauncher.launch("image/*")  }){ CameraIcon() }
                    }
                    Row{
                        Button(modifier = Modifier.padding(10.dp),onClick = {
                            if(animal.nom != "" && (animal.iconPath != null || animal.iconName != null)) {animalActivitesModel.updateAnimal(animal) }
                            else { Toast.makeText(context, "Le nom et l'icone de de votre compagnon ne doivent pas être vide !", Toast.LENGTH_SHORT) .show()}
                        } )
                        {Text("Valider")}

                        Button(modifier = Modifier.padding(10.dp), onClick = {isDialogOpenModif = false}){Text("Annuler")}
                    }
                }
            }

        )
    }
    //modification de l'animal

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

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp) // Ajout d'espacement uniforme
    ) {
        item{
            Row {
                Button(
                    onClick = { onDelete = true },
                    modifier = Modifier.padding(15.dp)
                ) { Text("Supprimer") }
                Button(
                    onClick = { isDialogOpenModif = true },
                    modifier = Modifier.padding(15.dp)
                ) { Text("Modifier") }
            }
        }
        item{
            AnimalImage(animal.iconName, animal.iconPath)
            Text(animal.nom, modifier = Modifier.padding(20.dp))
        }
        item{
            Text(espece, modifier = Modifier.padding(20.dp))
        }
        item {

            Text("liste des activites: ")
        }
        item{
            ShowListActivity(listActivites,activitesPlanifieesViewModel)
        }
        item{
            Button(onClick = {navigateTo(navController,"add_activites", false)}) { Text("Ajouter une activité") }
        }
    }
}

@Composable
fun AnimalImage(iconName: String?, customIconPath: String?) {
    if (customIconPath != null) {
        AsyncImage(
            model = customIconPath,
            contentDescription = "Custom Icon",
            modifier = Modifier
                .size(128.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    } else if (iconName != null) {
        val context = LocalContext.current
        val resId = remember(iconName) {
            animals[iconName]
        }
        Image(
            painter = painterResource(id = resId!!),//gestion erreur peut etre ???????????????????????????
            contentDescription = "Default Icon",
            modifier = Modifier
                .size(128.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}
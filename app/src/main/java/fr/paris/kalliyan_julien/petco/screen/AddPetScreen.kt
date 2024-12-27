package fr.paris.kalliyan_julien.petco.screen

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import fr.paris.kalliyan_julien.petco.data.Especes
import fr.paris.kalliyan_julien.petco.navigateTo
import fr.paris.kalliyan_julien.petco.ui.AnimalEspeceViewModel
import fr.paris.kalliyan_julien.petco.ui.MainViewModel
import fr.paris.kalliyan_julien.petco.ui.theme.CameraIcon
import fr.paris.kalliyan_julien.petco.ui.theme.animals
import fr.paris.kalliyan_julien.petco.ui.theme.copyImageToAppDirectory
import kotlinx.coroutines.flow.forEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddpetScreen(model : AnimalEspeceViewModel, navController: NavHostController){

    val allespece by model.allEspeceFlow.collectAsState(emptyList())
    var expanded by remember { mutableStateOf(false) }
    var esp by remember { mutableStateOf("sélectionnez une espèce") }
    var esptmp by model.espece
    var adding by model.add
    var ico by model.selectedIconIndex
    var iconPath by model.iconPath
    val context = LocalContext.current
    val listanimals = animals.toList()
    var add_espece by model.add_espece
    var isDialogOpen by model.isDialogOpen

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                val imagePath = copyImageToAppDirectory(context, it)
                if(imagePath != null) {
                    iconPath = imagePath
                } else {
                    Log.e("GalleryLauch", "failed saving image")
                }
            }
        }
    )

    //ajout animal dans la bd

    if(adding) {
        if(model.name.value == "" || esp == "sélectionnez une espèce" || (ico == -1 && iconPath == "")){
            Toast.makeText(context, "Nom, espèce ou icone ne doivent pas être vide !", Toast.LENGTH_SHORT) .show()
            Log.d("test", "${model.name.value}, $esp, $ico")
            adding = false
        }
        else {
            if(iconPath != "") {
                model.addAnimal(
                    model.name.value.trim(),
                    esptmp.id,
                    null,
                    iconPath
                )
            }
            else {
                model.addAnimal(
                    model.name.value.trim(),
                    esptmp.id,
                    listanimals[ico].first,
                    null
                )
            }
            model.name.value = ""
            iconPath = ""
            esp = "sélectionnez une espèce"
            ico = -1
            iconPath = ""
            navigateTo(navController, "home", true)

        }
    }
    //ajout animal dans la bd

    //ajout d'une espece dans la bd
    if(isDialogOpen){
        BasicAlertDialog(
            onDismissRequest = {isDialogOpen = false},
            modifier = Modifier.padding(20.dp).background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp)),
            content = {
                Column (modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Row{
                        Text("Entrer un nom  d'espèce", modifier = Modifier.padding(bottom = 8.dp))
                    }
                    Row{
                       OutlinedTextField(modifier = Modifier.fillMaxWidth(),value = add_espece, onValueChange = { add_espece = it}, label = { Text("nom de l'espèce") }, singleLine = true)
                    }
                    Row{
                        Button(modifier = Modifier.padding(10.dp),onClick = {
                            if(add_espece != "") model.addEspece(add_espece.trim())
                            else { Toast.makeText(context, "Le nom de l'espèce ne peut pas être vide !", Toast.LENGTH_SHORT) .show()}
                        } )
                        {Text("Valider")}

                        Button(modifier = Modifier.padding(10.dp), onClick = {isDialogOpen = false}){Text("Annuler")}
                    }
                }
            }

        )
    }
    //ajout d'une espece dans la bd

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Row{
            Text("nom de votre nouveaux compagnon: ", modifier = Modifier.padding(10.dp))
        }

        Row {
            OutlinedTextField(value = model.name.value, onValueChange = {model.name.value = it}, label = { Text("nom") } )
        }

        Row(modifier = Modifier.padding(20.dp)) {

            Button(onClick = { isDialogOpen = true }){ Icon(Icons.Filled.Add,"add species") }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {expanded = ! expanded}
            ) {
                TextField(
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    value = esp,
                    onValueChange = {esp = it},
                    label = { Text("Espèces") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false}
                ) {
                    for (e in allespece){
                        DropdownMenuItem(
                            onClick = {
                                esptmp = e
                                expanded = false
                                esp = e.nom      },
                            text = { Text(text = e.nom) }
                        )

                    }
                }
            }


        }
        Row {
            Text("Choississez une icone ou prenez une photo de votre compagnon : ", modifier = Modifier.padding(10.dp))
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
                    items(listanimals.size) { index ->
                        val isSelected = (index == model.selectedIconIndex.intValue)
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Color.Blue else Color.White)
                                .clickable {
                                    ico = index
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = listanimals[index].second),
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
            Button(modifier = Modifier.padding(10.dp), onClick = {  galleryLauncher.launch("image/*")  }){CameraIcon()}
            Button(modifier = Modifier.padding(10.dp),onClick = { adding = true }){ Text("valider") }

        }

    }

}
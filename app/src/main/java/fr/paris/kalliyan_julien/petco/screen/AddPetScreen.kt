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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
    var expanded by rememberSaveable { mutableStateOf(false) }
    var esp by rememberSaveable { mutableStateOf("sélectionnez une espèce") }
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

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp) // Ajout d'espacement uniforme
    ) {
        // Champ de texte pour le nom
        item {
            Text(
                "Nom de votre nouveau compagnon:",
                modifier = Modifier.padding(20.dp)
            )
        }

        item {
            OutlinedTextField(
                value = model.name.value,
                onValueChange = { model.name.value = it },
                label = { Text("Nom") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )
        }

        // Bouton + liste déroulante
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedIconButton(onClick = { isDialogOpen = true }) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Add species",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        modifier = Modifier
                            .menuAnchor()
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(10.dp),
                        readOnly = true,
                        value = esp,
                        onValueChange = { esp = it },
                        label = { Text("Espèces") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        allespece.forEach { e ->
                            DropdownMenuItem(
                                onClick = {
                                    esptmp = e
                                    expanded = false
                                    esp = e.nom
                                },
                                text = { Text(text = e.nom) }
                            )
                        }
                    }
                }
            }
        }

        // Message et grille d'icônes
        item {
            Text(
                "Choisissez une icône ou prenez une photo de votre compagnon:",
                modifier = Modifier.padding(10.dp)
            )
        }

        // Grille d'icônes
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Fixe une hauteur à la grille
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.onBackground, shape = RoundedCornerShape(16.dp))
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize().padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(listanimals.size) { index ->
                        val isSelected = (index == model.selectedIconIndex.intValue)
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurface
                                )
                                .clickable {
                                    ico = if (ico == index) -1 else index
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = listanimals[index].second),
                                contentDescription = "Icon $index",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(45.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .fillMaxSize()
                                    .padding(5.dp)
                            )
                        }
                    }
                }
            }
        }

        // Boutons de validation et prise de photo
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedIconButton(
                    onClick = { galleryLauncher.launch("image/*") }
                ) {
                    CameraIcon()
                }

                Button(onClick = { adding = true }) {
                    Text("Valider")
                }
            }
        }
    }


}
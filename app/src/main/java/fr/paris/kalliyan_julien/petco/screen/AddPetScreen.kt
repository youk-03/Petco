package fr.paris.kalliyan_julien.petco.screen

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
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.paris.kalliyan_julien.petco.ui.AnimalEspeceViewModel
import fr.paris.kalliyan_julien.petco.ui.MainViewModel
import fr.paris.kalliyan_julien.petco.ui.theme.CameraIcon
import kotlinx.coroutines.flow.forEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddpetScreen(model : AnimalEspeceViewModel){

    var expanded by remember { mutableStateOf(false) }
    var esp by model.espece
    var esptmp by model.especetmp
    val allespece by model.allEspeceFlow.collectAsState(emptyList())

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

            OutlinedTextField(value = model.name.value, onValueChange = {model.name.value = it}, label = { Text("nom") } )

        }
        Row {

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
                                expanded = false },
                            text = { Text(text = e.nom) }
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
                                    model.selectedIconIndex.intValue = index // Met à jour l'icône sélectionnée
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
            Button(modifier = Modifier.padding(10.dp), onClick = {}){CameraIcon()}
            Button(modifier = Modifier.padding(10.dp),onClick = {}){ Text("valider") }
        }

    }

}
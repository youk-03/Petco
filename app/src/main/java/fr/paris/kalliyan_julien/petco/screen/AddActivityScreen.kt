package fr.paris.kalliyan_julien.petco.screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fr.paris.kalliyan_julien.petco.navigateTo
import fr.paris.kalliyan_julien.petco.ui.AnimalActiviteesViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActivityScreen(model : AnimalActiviteesViewModel, navController: NavHostController){

    val context = LocalContext.current

    var hebdo by model.hebdo
    var daily by model.daily
    var unique by model.unique

    var notes by model.notes

    var activite by remember { mutableStateOf("sélectionnez une activité") }
    val allactivites by model.allActivitesFlow.collectAsState(emptyList())
    var activitetmp by model.current_activite

    var isDialogOpen by model.isDialogOpen
    var add_activity by model.add_activity

    var expanded by remember { mutableStateOf(false) }

    var adding by model.adding

    if(adding){
        if(activite == "sélectionnez une activité" || (!hebdo && !daily && !unique) ){
            Toast.makeText(context, "l'activité et/ou la fréquence ne peuvent pas être vide !", Toast.LENGTH_SHORT) .show()
            adding = false
        }
        else {
            model.addActivitesPlanifiees(notes,context)
            activite = "sélectionnez une activité"
            daily = false
            hebdo = false
            unique = false
            notes = ""
            navigateTo(navController, "animals", false)

        }
    }


    //ajout d'une actvites dans la bd
    if(isDialogOpen){
        BasicAlertDialog(
            onDismissRequest = {isDialogOpen = false},
            modifier = Modifier.padding(20.dp).background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp)),
            content = {
                Column (modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Row{
                        Text("Entrer un nom  d'activité", modifier = Modifier.padding(bottom = 8.dp))
                    }
                    Row{
                        OutlinedTextField(modifier = Modifier.fillMaxWidth(),value = add_activity, onValueChange = { add_activity = it}, label = { Text("nom de l'activité") }, singleLine = true)
                    }
                    Row{
                        Button(modifier = Modifier.padding(10.dp),onClick = {
                            if(add_activity != "") model.addActivite(add_activity.trim())
                            else { Toast.makeText(context, "L'activité ne peut pas être vide !", Toast.LENGTH_SHORT) .show()}
                        } )
                        {Text("Valider")}

                        Button(modifier = Modifier.padding(10.dp), onClick = {isDialogOpen = false}){Text("Annuler")}
                    }
                }
            }

        )
    }
    //ajout d'une activites dans la bd

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    )
    {
        item {
            Row(modifier = Modifier.padding(20.dp)) {

                Button(onClick = { isDialogOpen = true }) { Icon(Icons.Filled.Add, "add activity") }

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        modifier = Modifier.menuAnchor(),
                        readOnly = true,
                        value = activite,
                        onValueChange = { activite = it },
                        label = { Text("Activités") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        for (a in allactivites) {
                            DropdownMenuItem(
                                onClick = {
                                    activitetmp = a
                                    expanded = false
                                    activite = a.nom
                                },
                                text = { Text(text = a.nom) }
                            )

                        }
                    }
                }


            }
        }

        item{
            OutlinedTextField(value = notes, onValueChange = {notes = it}, label = { Text("Notes") } )
        }
        item{
            Row {
                TimePicker(model.calendar.value, context)
                DatePicker(model.calendar.value, context)
            }
        }
        item{
            Text("Envoyer une notification :")
        }
        item{
            Row {
                //notif hebdo quotidienne ou unique on peut pas deselctionner (c nul)
                RadioButton(enabled = true, selected = hebdo, onClick = {
                    hebdo = !hebdo
                    daily = false
                    unique = false
                })
                Text("Hebdo")

                RadioButton(enabled = true, selected = daily, onClick = {
                    daily = !daily
                    hebdo = false
                    unique = false
                })
                Text("Quotidienne")

                RadioButton(enabled = true, selected = unique, onClick = {
                    hebdo = false
                    daily = false
                    unique = !unique
                })
                Text("Unique")
            }
        }
        item{
            Button(onClick = {adding=true} ) { Text("Valider") }
        }

    }


}

@Composable
fun TimePicker(calendar: Calendar, context: Context) {
    val showTimeDialog = remember { mutableStateOf(false) }

    val timePickerFun = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        if(showTimeDialog.value){
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND,0)
            showTimeDialog.value=false
        }
    }

    Button(onClick = { showTimeDialog.value = true }) {
        Text("Choisir l'heure")
    }

    if (showTimeDialog.value) {
        TimePickerDialog(
            context,
            timePickerFun,
            calendar.get(Calendar.HOUR),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }
}

@Composable
fun DatePicker(calendar: Calendar,context: Context) {
    val showDateDialog = remember { mutableStateOf(false) }

    val datePickerFun = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        if(showDateDialog.value){
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            showDateDialog.value=false
        }
    }

    Button(onClick = { showDateDialog.value = true }) {
        Text("Choisir la date")
    }

    if (showDateDialog.value) {
        DatePickerDialog(
            context,
            datePickerFun,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}
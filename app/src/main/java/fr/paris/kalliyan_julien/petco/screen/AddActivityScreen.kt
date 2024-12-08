package fr.paris.kalliyan_julien.petco.screen

import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.paris.kalliyan_julien.petco.ui.AnimalActiviteesViewModel
import fr.paris.kalliyan_julien.petco.ui.AnimalEspeceViewModel
import java.util.Calendar

@Composable
fun AddActivityScreen(model : AnimalActiviteesViewModel){
    var hebdo by model.hebdo
    var daily by model.daily
    var unique by model.unique
    var activites by model.activites
    var notes by model.notes

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    )
    {
        Row{
            OutlinedTextField(value = activites, onValueChange = {activites = it}, label = { Text("Actitivés") } )
        }

        Row{
            OutlinedTextField(value = notes, onValueChange = {notes = it}, label = { Text("Notes") } )
        }
        Row{
            TimePicker { hour, minute ->
                Log.d("TimePicker", "Selected Time: $hour:$minute")
            }
        }
        Row{
            Text("Envoyer une notification :")
        }
        Row{
            //notif hebdo quotidienne ou unique
            RadioButton(enabled = true,selected = hebdo, onClick = {
                hebdo = true
                daily = false
                unique = false
            })
            Text("Hebdomadaire")

            RadioButton(enabled = true,selected = daily, onClick = {
                daily = true
                hebdo = false
                unique = false
            })
            Text("Quotidienne")

            RadioButton(enabled = true,selected = unique, onClick = {
                hebdo = false
                daily = false
                unique = true
            })
            Text("Unique")
        }
        Row{
            Button(onClick = {/*utiliser une varible ajouter dont la valeur changera ici et si toute les entrées == correctes ajouter à la bd*/} ) { Text("Valider") }
        }

    }


}

@Composable
fun TimePicker(onTimeSelected: (hour: Int, minute: Int) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val hour = remember { mutableStateOf(calendar.get(Calendar.HOUR_OF_DAY)) }
    val minute = remember { mutableStateOf(calendar.get(Calendar.MINUTE)) }

    val showDialog = remember { mutableStateOf(false) }

    Button(onClick = { showDialog.value = true }) {
        Text("Choisir l'heure")
    }

    // TimePickerDialog
    if (showDialog.value) {
        TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                hour.value = selectedHour
                minute.value = selectedMinute
                showDialog.value = false
                onTimeSelected(selectedHour, selectedMinute)
            },
            hour.value,
            minute.value,
            true
        ).show()
    }
}
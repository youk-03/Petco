package fr.paris.kalliyan_julien.petco.screen

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fr.paris.kalliyan_julien.petco.ui.MainViewModel
import fr.paris.kalliyan_julien.petco.ui.theme.SettingsManager
import fr.paris.kalliyan_julien.petco.ui.theme.ThemeType


@Composable
    fun SettingsScreen(modifier: Modifier = Modifier, settingsManager: SettingsManager, model : MainViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Button(onClick = {model.changeTheme(ThemeType.AUTUMN, settingsManager = settingsManager)}) {
            Text("Autumn")
        }
        Button(onClick = {model.changeTheme(ThemeType.FOREST, settingsManager = settingsManager)}) {
            Text("Forest")
        }
        Button(onClick = {model.changeTheme(ThemeType.VIOLET_ROSE, settingsManager = settingsManager)}) {
            Text("Violet")
        }

    }
    }

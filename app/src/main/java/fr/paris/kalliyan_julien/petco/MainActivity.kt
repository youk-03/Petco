package fr.paris.kalliyan_julien.petco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.paris.kalliyan_julien.petco.screen.AddpetScreen
import fr.paris.kalliyan_julien.petco.screen.AnimalScreen
import fr.paris.kalliyan_julien.petco.screen.HomeScreen
import fr.paris.kalliyan_julien.petco.screen.PicScreen
import fr.paris.kalliyan_julien.petco.screen.SettingsScreen
import fr.paris.kalliyan_julien.petco.ui.AnimalEspeceViewModel
import fr.paris.kalliyan_julien.petco.ui.MainViewModel
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
fun MainPage(name: String, modifier: Modifier = Modifier, model: MainViewModel = viewModel(), animal_espece_model: AnimalEspeceViewModel = viewModel()) {
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
            composable("add_pet", enterTransition = { fadeIn() }, exitTransition = { fadeOut() }) { AddpetScreen(animal_espece_model) }
        }
    }
}

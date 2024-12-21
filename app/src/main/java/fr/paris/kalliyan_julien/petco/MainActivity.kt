package fr.paris.kalliyan_julien.petco

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
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
import fr.paris.kalliyan_julien.petco.data.BD
import fr.paris.kalliyan_julien.petco.screen.ActivitesScreen
import fr.paris.kalliyan_julien.petco.screen.AddActivityScreen
import fr.paris.kalliyan_julien.petco.screen.AddpetScreen
import fr.paris.kalliyan_julien.petco.screen.ActivitesScreen
import fr.paris.kalliyan_julien.petco.screen.AnimalScreen
import fr.paris.kalliyan_julien.petco.screen.HomeScreen
import fr.paris.kalliyan_julien.petco.screen.PicScreen
import fr.paris.kalliyan_julien.petco.screen.SettingsScreen
import fr.paris.kalliyan_julien.petco.ui.AnimalActiviteesViewModel
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

private fun isFirstLaunch(context : Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("app_prefs", MODE_PRIVATE)
    val isFirstLaunch = sharedPreferences.getBoolean("is_first_launch", true)

    if (isFirstLaunch) {
        // Marquez comme non-première ouverture
        sharedPreferences.edit().putBoolean("is_first_launch", false).apply()
    }

    return isFirstLaunch
}



@Composable
fun MainPage(name: String, modifier: Modifier = Modifier, model: MainViewModel = viewModel(), animalEspeceModel: AnimalEspeceViewModel = viewModel(), animalActivitesModel : AnimalActiviteesViewModel = viewModel()) {
    val context = LocalContext.current
//    if (isFirstLaunch(context)) {
//        model.loadDefaultDataBase()
//        Log.d("bd", "full")
//    }

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
            composable("home", enterTransition = { slideInHorizontally() }, exitTransition = { slideOutHorizontally()}) { HomeScreen(navController,model, animalActivitesModel) }
            composable("pictures", enterTransition = { slideInHorizontally() }, exitTransition = { slideOutHorizontally() }) { PicScreen() }
            composable("animals", enterTransition = { slideInHorizontally() }, exitTransition = { slideOutHorizontally() }) { ActivitesScreen(animalActivitesModel, navController, model) }
            composable("settings", enterTransition = { slideInHorizontally() }, exitTransition = { slideOutHorizontally() }) { SettingsScreen() }
            composable("add_pet", enterTransition = { fadeIn() }, exitTransition = { fadeOut() }) { AddpetScreen(animalEspeceModel) }
            composable("add_activites", enterTransition = { fadeIn() }, exitTransition = { fadeOut() }) { AddActivityScreen(animalActivitesModel) }
            composable("animal", enterTransition = { fadeIn() }, exitTransition = { fadeOut() }) { AnimalScreen(animalActivitesModel, model, navController)}
        }
    }
}

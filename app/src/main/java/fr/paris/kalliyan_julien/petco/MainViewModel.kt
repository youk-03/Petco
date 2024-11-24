package fr.paris.kalliyan_julien.petco

import android.app.Application
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

class MainViewModel(application: Application) : AndroidViewModel(application)  {

    //pages

    @Composable
    fun HomeScreen(modifier: Modifier = Modifier) {
        Text("home")
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
    fun AnimalScreen(modifier: Modifier = Modifier) {
        Text("animal")
    }

    //pages



    //NAVBAR

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainTopBar() = CenterAlignedTopAppBar(
        title = { Text("PetCo", style = MaterialTheme.typography.displayLarge) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
    )

    @Composable
    fun MainBottomBar(navController: NavHostController) = NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        NavigationBarItem(selected = currentRoute == "home",
            onClick = { navController.navigate("home") { launchSingleTop = true } },
            icon = { Icon(Icons.Filled.Home, "home") })
        NavigationBarItem(selected = currentRoute == "pictures",
            onClick = { navController.navigate("pictures") { popUpTo("home") } },
            icon = { CameraIcon() })
        NavigationBarItem(selected = currentRoute == "animals",
            onClick = { navController.navigate("animals") { popUpTo("home") } },
            icon = { PetIcon() })
        NavigationBarItem(selected = currentRoute == "settings",
            onClick = { navController.navigate("settings") { popUpTo("home") } },
            icon = { Icon(Icons.Filled.Settings, "settings") })

    }

//NAVBAR


//Icon

    @Composable
    fun CameraIcon() {
        Icon(
            painter = painterResource(id = R.drawable.camera),
            contentDescription = "Camera Icon",
            tint = Color.Black // Personnaliser la couleur
        )
    }
    @Composable
    fun PetIcon() {
        Icon(
            painter = painterResource(id = R.drawable.pet),
            contentDescription = "Camera Icon",
            tint = Color.Black // Personnaliser la couleur
        )
    }
//Icon
}
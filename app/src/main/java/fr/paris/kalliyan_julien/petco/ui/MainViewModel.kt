package fr.paris.kalliyan_julien.petco.ui

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import fr.paris.kalliyan_julien.petco.R
import fr.paris.kalliyan_julien.petco.data.Activites
import fr.paris.kalliyan_julien.petco.data.ActivitesEspeces
import fr.paris.kalliyan_julien.petco.data.BD
import fr.paris.kalliyan_julien.petco.data.Especes
import fr.paris.kalliyan_julien.petco.navigateTo
import fr.paris.kalliyan_julien.petco.ui.theme.CameraIcon
import fr.paris.kalliyan_julien.petco.ui.theme.PetIcon
import fr.paris.kalliyan_julien.petco.ui.theme.SettingsManager
import fr.paris.kalliyan_julien.petco.ui.theme.ThemeType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel(application: Application) : AndroidViewModel(application)  {

//    private val dao by lazy { BD.getDB(application) }
//    val especesdao = dao.EspecesDao()
//    val animauxdao = dao.AnimauxDao()
//    val activites_planifieesdao = dao.ActivitesPlanifieesDao()
//    val activites_especesdao = dao.ActivitesEspecesDao()
//    val activites_animauxdao = dao.ActivitesAnimauxDao()
//    private val bd by lazy { BD.getDB(application) }

    val message = listOf("Bonjour ! C'est un plaisir de vous voir ici.",
        "Bienvenue dans PetCo ! Où chaque animal est roi.",
        "Bonjour ! Nous sommes là pour vous aider à prendre soin de votre meilleur ami.",
        "Salut ! Votre compagnon à fourrure va adorer ce que nous avons en réserve.",
        "Bienvenue dans le monde des amoureux des animaux ! Prenez soin de vos compagnons avec amour.")

    val i = Random.nextInt(0,message.size)
    //val especes = listOf("chat", "chien", "poisson", "perroquet")

   // val picture = mutableStateOf("")
    //var espece = mutableStateOf(especes[0])

//    val animals = listOf(
//        "Naya" to R.drawable.naya,
//        "Gibs" to R.drawable.chat,
//        "chat_noir" to R.drawable.chatn,
//        "dog" to R.drawable.dog,
//        "hamser" to R.drawable.hamster,
//        "fish" to R.drawable.fish,
//        "bunny" to R.drawable.lapin
//    )



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
            onClick = { navigateTo(navController,"home", true) },
            icon = { Icon(Icons.Filled.Home, "home") })
        NavigationBarItem(selected = currentRoute == "animals",
            onClick = { navigateTo(navController,"animals", false)},
            icon = { PetIcon() })
        NavigationBarItem(selected = currentRoute == "settings",
            onClick = { navigateTo(navController,"settings", false) },
            icon = { Icon(Icons.Filled.Settings, "settings") })

    }

//NAVBAR

    fun changeTheme(theme: ThemeType,  settingsManager: SettingsManager) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsManager.saveTheme(theme)
        }
    }


}
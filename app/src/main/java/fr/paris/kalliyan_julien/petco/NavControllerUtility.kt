package fr.paris.kalliyan_julien.petco

import androidx.navigation.NavController

fun navigateTo(navController: NavController, route: String, home: Boolean) {
    val currentDestination = navController.currentBackStackEntry?.destination?.route
    if (currentDestination != route) {
        navController.navigate(route) {
            if(home) launchSingleTop = true
            else  popUpTo("home")
        }
    }
}
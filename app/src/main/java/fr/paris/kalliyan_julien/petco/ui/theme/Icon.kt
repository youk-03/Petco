package fr.paris.kalliyan_julien.petco.ui.theme

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import fr.paris.kalliyan_julien.petco.R

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
//img
val animals = mapOf(
    "Naya" to R.drawable.naya,
    "Gibs" to R.drawable.chat,
    "chat_noir" to R.drawable.chatn,
    "dog" to R.drawable.dog,
    "hamser" to R.drawable.hamster,
    "fish" to R.drawable.fish,
    "bunny" to R.drawable.lapin
)
//img
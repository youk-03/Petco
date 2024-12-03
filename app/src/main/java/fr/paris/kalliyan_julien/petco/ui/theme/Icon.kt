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
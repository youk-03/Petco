package fr.paris.kalliyan_julien.petco.ui.theme

import android.content.Context
import android.net.Uri
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import fr.paris.kalliyan_julien.petco.R
import java.io.File

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
    "naya" to R.drawable.naya,
    "gibs" to R.drawable.gibs,
    "chat" to R.drawable.chat,
    "chien" to R.drawable.chien,
    "hamster" to R.drawable.hamster,
    "poisson" to R.drawable.poisson,
    "lapin" to R.drawable.lapin
)
//img

fun copyImageToAppDirectory(context: Context, uri: Uri): String? {
    try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val fileName = "animal_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName) // Stockage interne de l'app

        file.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }

        return file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}
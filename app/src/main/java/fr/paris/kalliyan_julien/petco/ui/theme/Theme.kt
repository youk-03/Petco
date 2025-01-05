package fr.paris.kalliyan_julien.petco.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fr.paris.kalliyan_julien.petco.R

val Montserrat = FontFamily(Font(R.font.montserrat))
val OpenSans = FontFamily(Font(R.font.opensans))

val CustomTypography = Typography(
    titleLarge = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = Montserrat
    ),
    titleMedium = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = Montserrat
    ),
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = OpenSans
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = OpenSans
    )
)

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun PetCoTheme(
    theme: ThemeType,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = when (theme) {
        ThemeType.FOREST ->  ForestDarkColorScheme
        ThemeType.AUTUMN -> AutumnRedDarkColorScheme
        ThemeType.VIOLET_ROSE -> VioletRoseDarkColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = CustomTypography,
        content = content
    )
}


enum class ThemeType {
    FOREST,
    AUTUMN,
    VIOLET_ROSE
}


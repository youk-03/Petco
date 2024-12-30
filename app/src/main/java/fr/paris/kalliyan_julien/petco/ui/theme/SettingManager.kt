package fr.paris.kalliyan_julien.petco.ui.theme
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow

object PreferencesKeys {
    val THEME_KEY = stringPreferencesKey("theme_key")
}

val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsManager(private val context: Context) {

    private val dataStore = context.dataStore

 
    val theme: Flow<ThemeType> = dataStore.data
        .map { preferences ->
            val theme = preferences[PreferencesKeys.THEME_KEY] ?: ThemeType.FOREST.name
            ThemeType.valueOf(theme)
        }


    suspend fun saveTheme(theme: ThemeType) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_KEY] = theme.name
        }
    }
}
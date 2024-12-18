package com.example.simplecalculator.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map


object DataStore {
    private val Context.appDataStore by preferencesDataStore(name = "app_preferences")
    val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
    var isDarkTheme = false

    suspend fun <T> saveToDataStore(key: Preferences.Key<T>, value: T, context: Context) {
        context.appDataStore.edit {
            it[key] = value
        }
    }

    suspend fun <T> getFromDataStore(key: Preferences.Key<T>, context: Context): T? {
        return context.appDataStore.data
            .catch { throwable ->
            }.map {
                it[key]
            }
            .firstOrNull()
    }

}
package com.example.simplecalculator

import android.app.Application
import com.example.simplecalculator.utils.DataStore
import com.example.simplecalculator.utils.DataStore.DARK_THEME_KEY
import com.example.simplecalculator.utils.DataStore.getFromDataStore
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
@HiltAndroidApp
class MyApplication : Application() {
    var job: Job? = null

    override fun onCreate() {
        super.onCreate()
        job = CoroutineScope(Dispatchers.IO).launch {
            val isDarkTheme =
                getFromDataStore(key = DARK_THEME_KEY, context = this@MyApplication) ?: false
            DataStore.isDarkTheme = isDarkTheme
        }
    }
}
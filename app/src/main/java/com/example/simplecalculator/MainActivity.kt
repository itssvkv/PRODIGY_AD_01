package com.example.simplecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.simplecalculator.presentation.MainScreen
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme
import com.example.simplecalculator.utils.DataStore.DARK_THEME_KEY
import com.example.simplecalculator.utils.DataStore.getFromDataStore
import com.example.simplecalculator.utils.DataStore.isDarkTheme
import com.example.simplecalculator.utils.DataStore.saveToDataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val scope = rememberCoroutineScope()

            var darkTheme by remember {
                mutableStateOf(isDarkTheme)
            }
            SimpleCalculatorTheme(darkTheme = darkTheme, dynamicColor = false) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    MainScreen(
                        darkTheme = darkTheme,
                        onThemeUpdated = {
                            darkTheme = !darkTheme
                            scope.launch {
                                saveToDataStore(
                                    key = DARK_THEME_KEY,
                                    value = darkTheme,
                                    context = context
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimpleCalculatorTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            MainScreen(
                darkTheme = true,
                onThemeUpdated = { }
            )
        }
    }
}
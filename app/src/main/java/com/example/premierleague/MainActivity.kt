package com.example.premierleague

import PremierLeagueNavGraph
import android.os.Bundle
import androidx.compose.material3.Text
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
 import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.premierleague.ui.theme.PremierLeagueMatchesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PremierLeagueApp()
        }
    }
}

@Composable
fun PremierLeagueApp() {
    var showSplash by remember { mutableStateOf(true) }

    // Показываем сплэш-скрин 2 секунды, затем скрываем
    LaunchedEffect(Unit) {
        delay(2000) // Задержка 2 секунды
        showSplash = false
    }

    PremierLeagueMatchesTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if (showSplash) {
                SplashScreen()
            } else {
                val navController = rememberNavController()
                PremierLeagueNavGraph(navController = navController)
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Кастомный сплэш-скрин (можно заменить на ваш дизайн)
        Text(
            text = "Premier League",
            style = MaterialTheme.typography.displayLarge,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    PremierLeagueMatchesTheme {
        SplashScreen()
    }
}


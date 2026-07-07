package com.rjnx.hyperengine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.rjnx.hyperengine.ui.screens.SplashScreen
import com.rjnx.hyperengine.ui.theme.RJNXHyperEngineTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        
        setContent {
            RJNXHyperEngineTheme {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent() {
    val viewModel: SplashViewModel = hiltViewModel()
    
    // Animated background color
    val backgroundColor = remember { Animatable(Color(0xFF0A0A0F)) }
    
    LaunchedEffect(Unit) {
        backgroundColor.animateTo(
            targetValue = Color(0xFF1A1A25),
            animationSpec = tween(durationMillis = 1000)
        )
    }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        SplashScreen(
            onSplashFinished = { viewModel.onSplashFinished() },
            backgroundColor = backgroundColor.value
        )
    }
}

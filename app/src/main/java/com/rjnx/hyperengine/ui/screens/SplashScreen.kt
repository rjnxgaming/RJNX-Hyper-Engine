package com.rjnx.hyperengine.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rjnx.hyperengine.R
import com.rjnx.hyperengine.navigation.Routes
import com.rjnx.hyperengine.ui.theme.JetBrainsMono
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit,
    backgroundColor: Color
) {
    var showMainContent by remember { mutableStateOf(false) }
    
    // Animated values
    val progress = rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    
    val loadingProgress = rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    
    LaunchedEffect(Unit) {
        delay(3000)
        showMainContent = true
        delay(500)
        onSplashFinished()
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Animated background gradient
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            
            val gradient = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF0A0A0F),
                    Color(0xFF1A1A25),
                    Color(0xFF252535)
                ),
                startY = 0f,
                endY = canvasHeight
            )
            
            drawRect(gradient)
            
            // Animated scan lines
            val scanLineY = canvasHeight * progress.value
            drawLine(
                color = Color(0x4000FFFF),
                start = Offset(0f, scanLineY),
                end = Offset(canvasWidth, scanLineY),
                strokeWidth = 2f
            )
            
            drawLine(
                color = Color(0x40FF00FF),
                start = Offset(0f, scanLineY - 100f),
                end = Offset(canvasWidth, scanLineY - 100f),
                strokeWidth = 1f
            )
        }
        
        // Cyberpunk grid overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0x2000FFFF),
                            Color(0x20FF00FF),
                            Color.Transparent
                        ),
                        startY = 0f,
                        endY = 1000f
                    )
                )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                // Animated logo
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val center = Offset(size.width / 2, size.height / 2)
                    val radius = size.width / 2 * 0.8f
                    
                    // Outer ring
                    drawCircle(
                        color = Color(0xFF33334A),
                        radius = radius + 4f,
                        center = center
                    )
                    
                    // Main circle with gradient
                    val circleGradient = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFF00FF),
                            Color(0xFF00FFFF)
                        ),
                        center = center,
                        radius = radius
                    )
                    drawCircle(
                        brush = circleGradient,
                        radius = radius,
                        center = center
                    )
                    
                    // Inner glow
                    drawCircle(
                        color = Color(0x80FFFFFF),
                        radius = radius * 0.6f,
                        center = center,
                        alpha = 0.3f
                    )
                    
                    // Animated pulse
                    val pulseRadius = radius * 0.8f * (0.5f + 0.5f * progress.value)
                    drawCircle(
                        color = Color(0xFFFFFFFF),
                        radius = pulseRadius,
                        center = center,
                        alpha = 0.1f + 0.1f * progress.value
                    )
                    
                    // H letter
                    val hWidth = 8f
                    val hHeight = 16f
                    drawLine(
                        color = Color.White,
                        start = Offset(center.x - hWidth, center.y - hHeight),
                        end = Offset(center.x - hWidth, center.y + hHeight),
                        strokeWidth = 3f
                    )
                    drawLine(
                        color = Color.White,
                        start = Offset(center.x + hWidth, center.y - hHeight),
                        end = Offset(center.x + hWidth, center.y + hHeight),
                        strokeWidth = 3f
                    )
                    drawLine(
                        color = Color.White,
                        start = Offset(center.x - hWidth, center.y),
                        end = Offset(center.x + hWidth, center.y),
                        strokeWidth = 3f
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Title
            Text(
                text = "RJNX HYPER ENGINE",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontFamily = JetBrainsMono,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    letterSpacing = 4.sp
                ),
                color = Color.White,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Subtitle with typing animation
            val subtitleText = "Initializing Quantum Core..."
            var displayedText by remember { mutableStateOf("") }
            
            LaunchedEffect(Unit) {
                subtitleText.forEachIndexed { index, char ->
                    delay(50)
                    displayedText += char
                }
            }
            
            Text(
                text = displayedText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = JetBrainsMono,
                    fontSize = 14.sp
                ),
                color = Color(0xFFB0B0C0),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Loading bar
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(4.dp)
                    .background(Color(0xFF252535))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(loadingProgress.value / 100f)
                        .height(4.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFFF00FF),
                                    Color(0xFF00FFFF)
                                )
                            )
                        )
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Loading percentage
            Text(
                text = "${loadingProgress.value.toInt()}%",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = JetBrainsMono,
                    fontSize = 12.sp
                ),
                color = Color(0xFF00FFFF),
                textAlign = TextAlign.Center
            )
        }
        
        // Glow effects
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(200.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0x80FF00FF),
                            Color.Transparent
                        ),
                        center = Offset(0.5f, 0.5f),
                        radius = 100f
                    )
                )
                .blur(100.dp)
        )
        
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(200.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0x8000FFFF),
                            Color.Transparent
                        ),
                        center = Offset(0.5f, 0.5f),
                        radius = 100f
                    )
                )
                .blur(100.dp)
        )
    }
}

package com.rjnx.hyperengine.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rjnx.hyperengine.ui.theme.JetBrainsMono
import kotlinx.coroutines.delay

@Composable
fun PerformanceScreen() {
    val viewModel: PerformanceViewModel = hiltViewModel()
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Header
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Performance Monitor",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = JetBrainsMono,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = "Real-time system monitoring and optimization",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = JetBrainsMono,
                        fontSize = 14.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        item {
            // Summary Cards
            PerformanceSummaryCards()
        }
        
        item {
            // Real-time Charts
            RealTimeCharts()
        }
        
        item {
            // Detailed Stats
            DetailedStatsSection()
        }
        
        item {
            // Optimization Actions
            OptimizationActions()
        }
    }
}

@Composable
fun PerformanceSummaryCards() {
    val summaryItems = listOf(
        PerformanceSummary("CPU Usage", "65%", 0.65f, Color(0xFFFF00FF)),
        PerformanceSummary("Memory", "4.2/8 GB", 0.525f, Color(0xFF00FFFF)),
        PerformanceSummary("GPU", "45%", 0.45f, Color(0xFFFF6600)),
        PerformanceSummary("Battery", "82°C", 0.82f, Color(0xFF44FF44))
    )
    
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(summaryItems) { item ->
            PerformanceSummaryCard(summary = item)
        }
    }
}

@Composable
fun PerformanceSummaryCard(summary: PerformanceSummary) {
    ElevatedCard(
        modifier = Modifier
            .width(120.dp)
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Circular Progress
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val canvasSize = size.minDimension
                    val strokeWidth = 6f
                    val radius = (canvasSize - strokeWidth) / 2
                    
                    // Background circle
                    drawCircle(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        radius = radius,
                        center = Offset(canvasSize / 2, canvasSize / 2),
                        style = Stroke(width = strokeWidth)
                    )
                    
                    // Progress circle
                    drawArc(
                        color = summary.color,
                        startAngle = -90f,
                        sweepAngle = 360f * summary.progress,
                        useCenter = false,
                        topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                        size = androidx.compose.ui.geometry.Size(
                            canvasSize - strokeWidth,
                            canvasSize - strokeWidth
                        ),
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                    
                    // Center text
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            "${(summary.progress * 100).toInt()}%",
                            center.x,
                            center.y + 8f,
                            android.graphics.Paint().apply {
                                textSize = 14.sp.toPx()
                                color = android.graphics.Color.WHITE
                                textAlign = android.graphics.Paint.Align.CENTER
                                typeface = android.graphics.Typeface.MONOSPACE
                            }
                        )
                    }
                }
            }
            
            Text(
                text = summary.label,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = JetBrainsMono,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Text(
                text = summary.value,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = JetBrainsMono,
                    fontSize = 10.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun RealTimeCharts() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Real-time Performance",
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = JetBrainsMono,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
        
        // CPU Usage Chart
        ChartCard(
            title = "CPU Usage",
            color = Color(0xFFFF00FF),
            dataPoints = listOf(0.6f, 0.65f, 0.7f, 0.68f, 0.62f, 0.65f, 0.7f, 0.68f)
        )
        
        // Memory Usage Chart
        ChartCard(
            title = "Memory Usage",
            color = Color(0xFF00FFFF),
            dataPoints = listOf(0.4f, 0.45f, 0.5f, 0.48f, 0.52f, 0.48f, 0.5f, 0.45f)
        )
        
        // GPU Usage Chart
        ChartCard(
            title = "GPU Usage",
            color = Color(0xFFFF6600),
            dataPoints = listOf(0.3f, 0.35f, 0.4f, 0.45f, 0.4f, 0.38f, 0.42f, 0.4f)
        )
    }
}

@Composable
fun ChartCard(title: String, color: Color, dataPoints: List<Float>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = JetBrainsMono,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            
            // Line Chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    val spacing = canvasWidth / (dataPoints.size - 1)
                    
                    // Draw grid lines
                    repeat(4) { i ->
                        val y = canvasHeight * (i + 1) / 5
                        drawLine(
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            start = Offset(0f, y),
                            end = Offset(canvasWidth, y),
                            strokeWidth = 1f
                        )
                    }
                    
                    // Draw data points
                    val path = Path().apply {
                        dataPoints.forEachIndexed { index, value ->
                            val x = index * spacing
                            val y = canvasHeight * (1 - value)
                            if (index == 0) {
                                moveTo(x, y)
                            } else {
                                lineTo(x, y)
                            }
                        }
                    }
                    
                    drawPath(
                        path = path,
                        color = color,
                        style = Stroke(width = 3f)
                    )
                    
                    // Draw filled area
                    val fillPath = Path().apply {
                        dataPoints.forEachIndexed { index, value ->
                            val x = index * spacing
                            val y = canvasHeight * (1 - value)
                            if (index == 0) {
                                moveTo(x, canvasHeight)
                                lineTo(x, y)
                            } else {
                                lineTo(x, y)
                            }
                        }
                        lineTo(canvasWidth, canvasHeight)
                        lineTo(0f, canvasHeight)
                        close()
                    }
                    
                    drawPath(
                        path = fillPath,
                        color = color.copy(alpha = 0.2f)
                    )
                    
                    // Draw data points
                    dataPoints.forEachIndexed { index, value ->
                        val x = index * spacing
                        val y = canvasHeight * (1 - value)
                        drawCircle(
                            color = color,
                            center = Offset(x, y),
                            radius = 4f
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DetailedStatsSection() {
    val stats = listOf(
        DetailStat("CPU Cores", "8", "Active: 4"),
        DetailStat("Total RAM", "8 GB", "Used: 4.2 GB"),
        DetailStat("Storage", "256 GB", "Free: 128 GB"),
        DetailStat("GPU", "Adreno 640", "Frequency: 675 MHz"),
        DetailStat("Battery", "4500 mAh", "Health: 92%"),
        DetailStat("Thermal", "82°C", "Status: Normal")
    )
    
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Detailed Statistics",
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = JetBrainsMono,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
        
        stats.forEach { stat ->
            DetailStatCard(stat = stat)
        }
    }
}

@Composable
fun DetailStatCard(stat: DetailStat) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stat.label,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = JetBrainsMono,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stat.subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = JetBrainsMono
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Text(
                text = stat.value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = JetBrainsMono,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Composable
fun OptimizationActions() {
    val actions = listOf(
        OptimizationAction("CPU Boost", "Increase CPU performance by 20%", "⚡"),
        OptimizationAction("Memory Clean", "Free up unused memory", "🧹"),
        OptimizationAction("Thermal Control", "Reduce device temperature", "❄️"),
        OptimizationAction("Battery Saver", "Extend battery life", "🔋")
    )
    
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Quick Optimization",
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = JetBrainsMono,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
        
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(actions) { action ->
                OptimizationActionCard(action = action)
            }
        }
    }
}

@Composable
fun OptimizationActionCard(action: OptimizationAction) {
    var isRunning by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .width(140.dp)
            .clickable {
                isRunning = !isRunning
                // Start optimization
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = if (isRunning) {
            androidx.compose.foundation.BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            null
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = action.icon,
                style = MaterialTheme.typography.titleLarge
            )
            
            Text(
                text = action.title,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = JetBrainsMono,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = action.description,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = JetBrainsMono,
                    fontSize = 10.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            
            if (isRunning) {
                // Animated progress
                val infiniteTransition = rememberInfiniteTransition()
                val progress by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
                
                androidx.compose.material3.LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}

// Data classes
data class PerformanceSummary(
    val label: String,
    val value: String,
    val progress: Float,
    val color: Color
)

data class DetailStat(
    val label: String,
    val value: String,
    val subtitle: String
)

data class OptimizationAction(
    val title: String,
    val description: String,
    val icon: String
)

package com.rjnx.hyperengine.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rjnx.hyperengine.navigation.BottomNavItems
import com.rjnx.hyperengine.navigation.NavGraph
import com.rjnx.hyperengine.navigation.Routes
import com.rjnx.hyperengine.ui.components.BottomNavigationBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    var showBottomBar by remember { mutableStateOf(true) }
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    LaunchedEffect(currentDestination) {
        showBottomBar = currentDestination?.hierarchy?.any { 
            it.route == Routes.HOME || 
            it.route == Routes.GAMES || 
            it.route == Routes.PERFORMANCE || 
            it.route == Routes.ADB || 
            it.route == Routes.AI || 
            it.route == Routes.SETTINGS
        } ?: false
    }
    
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut()
            ) {
                BottomNavigationBar(
                    items = BottomNavItems.items,
                    currentRoute = currentDestination?.route ?: Routes.HOME,
                    onItemClick = { route ->
                        navController.navigateToBottomNav(route)
                    }
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            NavGraph.AppNavHost(
                navController = navController,
                startDestination = Routes.HOME
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    items: List<com.rjnx.hyperengine.navigation.BottomNavItem>,
    currentRoute: String,
    onItemClick: (String) -> Unit
) {
    val viewModel: MainViewModel = hiltViewModel()
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        MaterialTheme.colorScheme.surfaceContainer
                    )
                )
            )
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outline)
        )
        
        androidx.compose.material3.NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            items.forEach { item ->
                val isSelected = item.route == currentRoute
                
                androidx.compose.material3.NavigationBarItem(
                    selected = isSelected,
                    onClick = { onItemClick(item.route) },
                    icon = {
                        // Custom icon with emoji
                        androidx.compose.foundation.text.Text(
                            text = if (isSelected) item.selectedIcon else item.icon,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    },
                    label = {
                        androidx.compose.material3.Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    },
                    colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
                    )
                )
            }
        }
    }
}

// Helper for gradient brush
import androidx.compose.ui.graphics.Brush

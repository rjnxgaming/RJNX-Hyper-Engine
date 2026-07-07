package com.rjnx.hyperengine.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rjnx.hyperengine.ui.screens.ADBScreen
import com.rjnx.hyperengine.ui.screens.AIScreen
import com.rjnx.hyperengine.ui.screens.GamesScreen
import com.rjnx.hyperengine.ui.screens.HomeScreen
import com.rjnx.hyperengine.ui.screens.PerformanceScreen
import com.rjnx.hyperengine.ui.screens.SettingsScreen

object Routes {
    const val SPLASH = "splash"
    const val MAIN = "main"
    const val HOME = "home"
    const val GAMES = "games"
    const val PERFORMANCE = "performance"
    const val ADB = "adb"
    const val AI = "ai"
    const val SETTINGS = "settings"
}

object BottomNavItems {
    val items = listOf(
        BottomNavItem(
            route = Routes.HOME,
            label = "Home",
            icon = "🏠",
            selectedIcon = "🏡"
        ),
        BottomNavItem(
            route = Routes.GAMES,
            label = "Games",
            icon = "🎮",
            selectedIcon = "🕹️"
        ),
        BottomNavItem(
            route = Routes.PERFORMANCE,
            label = "Performance",
            icon = "⚡",
            selectedIcon = "⚡"
        ),
        BottomNavItem(
            route = Routes.ADB,
            label = "ADB",
            icon = "🔌",
            selectedIcon = "🔗"
        ),
        BottomNavItem(
            route = Routes.AI,
            label = "AI",
            icon = "🤖",
            selectedIcon = "🤖"
        ),
        BottomNavItem(
            route = Routes.SETTINGS,
            label = "Settings",
            icon = "⚙️",
            selectedIcon = "⚙️"
        )
    )
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: String,
    val selectedIcon: String
)

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Routes.SPLASH
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.SPLASH) {
            // Splash screen handled in MainActivity
        }
        
        composable(Routes.MAIN) {
            // Main screen with bottom navigation
        }
        
        composable(Routes.HOME) {
            HomeScreen()
        }
        
        composable(Routes.GAMES) {
            GamesScreen()
        }
        
        composable(Routes.PERFORMANCE) {
            PerformanceScreen()
        }
        
        composable(Routes.ADB) {
            ADBScreen()
        }
        
        composable(Routes.AI) {
            AIScreen()
        }
        
        composable(Routes.SETTINGS) {
            SettingsScreen()
        }
    }
}

fun NavHostController.navigateToBottomNav(route: String) {
    navigate(route) {
        popUpTo(findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavHostController.shouldShowBottomBar(route: String): Boolean {
    return when (route) {
        Routes.HOME,
        Routes.GAMES,
        Routes.PERFORMANCE,
        Routes.ADB,
        Routes.AI,
        Routes.SETTINGS -> true
        else -> false
    }
}

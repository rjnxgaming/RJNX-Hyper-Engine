package com.rjnx.hyperengine.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    
    private val _stats = MutableStateFlow<List<StatData>>(emptyList())
    val stats = _stats.asStateFlow()
    
    private val _recentActivities = MutableStateFlow<List<ActivityItem>>(emptyList())
    val recentActivities = _recentActivities.asStateFlow()
    
    private val _featuredGames = MutableStateFlow<List<GameItem>>(emptyList())
    val featuredGames = _featuredGames.asStateFlow()
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            // Load sample stats
            _stats.value = listOf(
                StatData("CPU", "65%", 0.65f, android.graphics.Color.parseColor("#FFFF00FF")),
                StatData("Memory", "4.2 GB", 0.7f, android.graphics.Color.parseColor("#FF00FFFF")),
                StatData("Storage", "128 GB", 0.85f, android.graphics.Color.parseColor("#FFFF6600")),
                StatData("Battery", "82°C", 0.82f, android.graphics.Color.parseColor("#FF44FF44"))
            )
            
            // Load sample activities
            _recentActivities.value = listOf(
                ActivityItem("Game Optimized", "Call of Duty Mobile", "5 min ago"),
                ActivityItem("Cache Cleaned", "2.4 GB freed", "1 hour ago"),
                ActivityItem("Performance Boost", "CPU +20%", "2 hours ago")
            )
            
            // Load sample games
            _featuredGames.value = listOf(
                GameItem("Genshin Impact", "RPG", "🎮", 4.8f),
                GameItem("Call of Duty Mobile", "FPS", "🔫", 4.9f),
                GameItem("PUBG Mobile", "Battle Royale", "🎯", 4.7f),
                GameItem("Free Fire", "Battle Royale", "🔥", 4.6f)
            )
        }
    }
    
    fun refreshData() {
        loadData()
    }
}

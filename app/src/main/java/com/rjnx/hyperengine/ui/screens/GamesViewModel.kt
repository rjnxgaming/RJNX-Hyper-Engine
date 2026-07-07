package com.rjnx.hyperengine.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor() : ViewModel() {
    
    private val _games = MutableStateFlow<List<GameItem>>(emptyList())
    val games = _games.asStateFlow()
    
    private val _favoriteGames = MutableStateFlow<List<GameItem>>(emptyList())
    val favoriteGames = _favoriteGames.asStateFlow()
    
    private val _recentGames = MutableStateFlow<List<GameItem>>(emptyList())
    val recentGames = _recentGames.asStateFlow()
    
    private val _searchResults = MutableStateFlow<List<GameItem>>(emptyList())
    val searchResults = _searchResults.asStateFlow()
    
    init {
        loadGames()
    }
    
    private fun loadGames() {
        viewModelScope.launch {
            // Sample games data
            val sampleGames = listOf(
                GameItem("Genshin Impact", "RPG", "🎮", 4.8f),
                GameItem("Call of Duty Mobile", "FPS", "🔫", 4.9f),
                GameItem("PUBG Mobile", "Battle Royale", "🎯", 4.7f),
                GameItem("Free Fire", "Battle Royale", "🔥", 4.6f),
                GameItem("Clash of Clans", "Strategy", "🏰", 4.5f),
                GameItem("Clash Royale", "Strategy", "👑", 4.6f),
                GameItem("Among Us", "Social", "👽", 4.4f),
                GameItem("Minecraft", "Adventure", "🪓", 4.9f),
                GameItem("Roblox", "Adventure", "🤖", 4.5f),
                GameItem("Fortnite", "Battle Royale", "🎯", 4.8f)
            )
            
            _games.value = sampleGames
            _favoriteGames.value = sampleGames.filterIndexed { index, _ -> index % 3 == 0 }
            _recentGames.value = sampleGames.take(5)
        }
    }
    
    fun searchGames(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                _searchResults.value = emptyList()
            } else {
                _searchResults.value = _games.value.filter {
                    it.name.contains(query, ignoreCase = true) ||
                    it.genre.contains(query, ignoreCase = true)
                }
            }
        }
    }
    
    fun toggleFavorite(game: GameItem) {
        viewModelScope.launch {
            val updatedFavorites = if (_favoriteGames.value.contains(game)) {
                _favoriteGames.value - game
            } else {
                _favoriteGames.value + game
            }
            _favoriteGames.value = updatedFavorites
        }
    }
    
    fun refreshGames() {
        loadGames()
    }
}

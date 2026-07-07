package com.rjnx.hyperengine.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AIViewModel @Inject constructor() : ViewModel() {
    
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages = _chatMessages.asStateFlow()
    
    private val _isTyping = MutableStateFlow(false)
    val isTyping = _isTyping.asStateFlow()
    
    private val _aiModel = MutableStateFlow("GPT-4")
    val aiModel = _aiModel.asStateFlow()
    
    private val formatter = DateTimeFormatter.ofPattern("HH:mm")
    
    init {
        // Add welcome message
        viewModelScope.launch {
            delay(500)
            addMessage(
                ChatMessage(
                    sender = "ai",
                    text = "Hello! I'm your AI Gaming Assistant. How can I help you today?",
                    timestamp = LocalDateTime.now().format(formatter)
                )
            )
        }
    }
    
    fun sendMessage(message: String) {
        viewModelScope.launch {
            // Add user message
            addMessage(
                ChatMessage(
                    sender = "user",
                    text = message,
                    timestamp = LocalDateTime.now().format(formatter)
                )
            )
            
            // Set typing state
            _isTyping.value = true
            
            // Simulate AI response delay
            delay(1000)
            
            // Generate AI response
            val response = generateResponse(message)
            
            _isTyping.value = false
            
            // Add AI response
            addMessage(
                ChatMessage(
                    sender = "ai",
                    text = response,
                    timestamp = LocalDateTime.now().format(formatter)
                )
            )
        }
    }
    
    private fun addMessage(message: ChatMessage) {
        _chatMessages.value = _chatMessages.value + message
    }
    
    private fun generateResponse(message: String): String {
        return when {
            message.contains("hello", ignoreCase = true) || 
            message.contains("hi", ignoreCase = true) -> {
                "Hello! How can I assist you with your gaming experience today?"
            }
            message.contains("game", ignoreCase = true) -> {
                "I can help you optimize your games for better performance. Which game would you like to optimize?"
            }
            message.contains("performance", ignoreCase = true) -> {
                "For better performance, try enabling Game Mode in the Performance section. This will boost your CPU and GPU."
            }
            message.contains("boost", ignoreCase = true) -> {
                "I can help boost your device performance. Would you like me to activate the CPU boost?"
            }
            message.contains("adb", ignoreCase = true) -> {
                "ADB (Android Debug Bridge) is a powerful tool for developers. You can use it to debug and control your device."
            }
            message.contains("thank", ignoreCase = true) -> {
                "You're welcome! Is there anything else I can help you with?"
            }
            else -> {
                "I'm here to help with all your gaming needs. What would you like to know?"
            }
        }
    }
    
    fun setAIModel(model: String) {
        viewModelScope.launch {
            _aiModel.value = model
        }
    }
    
    fun clearChat() {
        viewModelScope.launch {
            _chatMessages.value = emptyList()
        }
    }
    
    fun getGameRecommendations() {
        viewModelScope.launch {
            addMessage(
                ChatMessage(
                    sender = "ai",
                    text = "Here are some game recommendations based on your preferences:",
                    timestamp = LocalDateTime.now().format(formatter)
                )
            )
            
            delay(500)
            
            addMessage(
                ChatMessage(
                    sender = "ai",
                    text = "1. Genshin Impact - Open world RPG with stunning graphics\n2. Call of Duty Mobile - Fast-paced FPS action\n3. PUBG Mobile - Battle royale experience\n4. Minecraft - Creative sandbox game",
                    timestamp = LocalDateTime.now().format(formatter)
                )
            )
        }
    }
    
    fun optimizePerformance(game: String) {
        viewModelScope.launch {
            addMessage(
                ChatMessage(
                    sender = "ai",
                    text = "Optimizing performance for $game...",
                    timestamp = LocalDateTime.now().format(formatter)
                )
            )
            
            delay(1000)
            
            addMessage(
                ChatMessage(
                    sender = "ai",
                    text = "Performance optimization complete for $game! Your device should now run this game smoother with better FPS.",
                    timestamp = LocalDateTime.now().format(formatter)
                )
            )
        }
    }
}

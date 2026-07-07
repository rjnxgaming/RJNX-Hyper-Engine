package com.rjnx.hyperengine.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    
    private val _isDarkMode = MutableStateFlow(true)
    val isDarkMode = _isDarkMode.asStateFlow()
    
    private val _themeMode = MutableStateFlow("System")
    val themeMode = _themeMode.asStateFlow()
    
    private val _language = MutableStateFlow("English")
    val language = _language.asStateFlow()
    
    private val _pushNotifications = MutableStateFlow(true)
    val pushNotifications = _pushNotifications.asStateFlow()
    
    private val _gameNotifications = MutableStateFlow(true)
    val gameNotifications = _gameNotifications.asStateFlow()
    
    private val _autoOptimization = MutableStateFlow(true)
    val autoOptimization = _autoOptimization.asStateFlow()
    
    private val _backgroundMonitoring = MutableStateFlow(false)
    val backgroundMonitoring = _backgroundMonitoring.asStateFlow()
    
    val version = "1.0.0"
    
    init {
        loadSettings()
    }
    
    private fun loadSettings() {
        viewModelScope.launch {
            // Load from DataStore
            _isDarkMode.value = dataStoreManager.getDarkMode()
            _themeMode.value = if (_isDarkMode.value) "Dark" else "Light"
            _language.value = dataStoreManager.getLanguage()
            _pushNotifications.value = dataStoreManager.getPushNotifications()
            _gameNotifications.value = dataStoreManager.getGameNotifications()
            _autoOptimization.value = dataStoreManager.getAutoOptimization()
            _backgroundMonitoring.value = dataStoreManager.getBackgroundMonitoring()
        }
    }
    
    fun setDarkMode(darkMode: Boolean) {
        viewModelScope.launch {
            _isDarkMode.value = darkMode
            _themeMode.value = if (darkMode) "Dark" else "Light"
            dataStoreManager.setDarkMode(darkMode)
        }
    }
    
    fun toggleTheme() {
        viewModelScope.launch {
            val themes = listOf("System", "Light", "Dark")
            val currentIndex = themes.indexOf(_themeMode.value)
            val nextIndex = (currentIndex + 1) % themes.size
            _themeMode.value = themes[nextIndex]
            
            when (themes[nextIndex]) {
                "Dark" -> setDarkMode(true)
                "Light" -> setDarkMode(false)
                else -> setDarkMode(dataStoreManager.getSystemDarkMode())
            }
        }
    }
    
    fun setLanguage(language: String) {
        viewModelScope.launch {
            _language.value = language
            dataStoreManager.setLanguage(language)
        }
    }
    
    fun setPushNotifications(enabled: Boolean) {
        viewModelScope.launch {
            _pushNotifications.value = enabled
            dataStoreManager.setPushNotifications(enabled)
        }
    }
    
    fun setGameNotifications(enabled: Boolean) {
        viewModelScope.launch {
            _gameNotifications.value = enabled
            dataStoreManager.setGameNotifications(enabled)
        }
    }
    
    fun setAutoOptimization(enabled: Boolean) {
        viewModelScope.launch {
            _autoOptimization.value = enabled
            dataStoreManager.setAutoOptimization(enabled)
        }
    }
    
    fun setBackgroundMonitoring(enabled: Boolean) {
        viewModelScope.launch {
            _backgroundMonitoring.value = enabled
            dataStoreManager.setBackgroundMonitoring(enabled)
        }
    }
    
    fun clearCache() {
        viewModelScope.launch {
            // Clear cache
        }
    }
    
    fun resetSettings() {
        viewModelScope.launch {
            // Reset all settings to default
            _isDarkMode.value = true
            _themeMode.value = "System"
            _language.value = "English"
            _pushNotifications.value = true
            _gameNotifications.value = true
            _autoOptimization.value = true
            _backgroundMonitoring.value = false
            
            dataStoreManager.resetSettings()
        }
    }
}

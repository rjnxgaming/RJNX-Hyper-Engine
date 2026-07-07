package com.rjnx.hyperengine.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.AppScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val PREFERENCES_NAME = "rjnx_hyper_engine_preferences"
        
        // Keys
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
        private val LANGUAGE = stringPreferencesKey("language")
        private val PUSH_NOTIFICATIONS = booleanPreferencesKey("push_notifications")
        private val GAME_NOTIFICATIONS = booleanPreferencesKey("game_notifications")
        private val AUTO_OPTIMIZATION = booleanPreferencesKey("auto_optimization")
        private val BACKGROUND_MONITORING = booleanPreferencesKey("background_monitoring")
        private val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        private val LAST_VERSION = stringPreferencesKey("last_version")
    }
    
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)
    private val dataStore = context.dataStore
    
    // Dark Mode
    suspend fun setDarkMode(darkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE] = darkMode
        }
    }
    
    fun getDarkMode(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[DARK_MODE] ?: true // Default to dark mode
    }
    
    suspend fun getDarkModeValue(): Boolean {
        return getDarkMode().first()
    }
    
    // System Dark Mode
    fun getSystemDarkMode(): Boolean {
        // This would typically check system settings
        return true // Default to dark for cyberpunk theme
    }
    
    // Language
    suspend fun setLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE] = language
        }
    }
    
    fun getLanguage(): Flow<String> = dataStore.data.map { preferences ->
        preferences[LANGUAGE] ?: "English"
    }
    
    suspend fun getLanguageValue(): String {
        return getLanguage().first()
    }
    
    // Push Notifications
    suspend fun setPushNotifications(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PUSH_NOTIFICATIONS] = enabled
        }
    }
    
    fun getPushNotifications(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PUSH_NOTIFICATIONS] ?: true
    }
    
    suspend fun getPushNotificationsValue(): Boolean {
        return getPushNotifications().first()
    }
    
    // Game Notifications
    suspend fun setGameNotifications(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[GAME_NOTIFICATIONS] = enabled
        }
    }
    
    fun getGameNotifications(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[GAME_NOTIFICATIONS] ?: true
    }
    
    suspend fun getGameNotificationsValue(): Boolean {
        return getGameNotifications().first()
    }
    
    // Auto Optimization
    suspend fun setAutoOptimization(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[AUTO_OPTIMIZATION] = enabled
        }
    }
    
    fun getAutoOptimization(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[AUTO_OPTIMIZATION] ?: true
    }
    
    suspend fun getAutoOptimizationValue(): Boolean {
        return getAutoOptimization().first()
    }
    
    // Background Monitoring
    suspend fun setBackgroundMonitoring(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[BACKGROUND_MONITORING] = enabled
        }
    }
    
    fun getBackgroundMonitoring(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[BACKGROUND_MONITORING] ?: false
    }
    
    suspend fun getBackgroundMonitoringValue(): Boolean {
        return getBackgroundMonitoring().first()
    }
    
    // Onboarding
    suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED] = completed
        }
    }
    
    fun getOnboardingCompleted(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[ONBOARDING_COMPLETED] ?: false
    }
    
    suspend fun getOnboardingCompletedValue(): Boolean {
        return getOnboardingCompleted().first()
    }
    
    // Last Version
    suspend fun setLastVersion(version: String) {
        dataStore.edit { preferences ->
            preferences[LAST_VERSION] = version
        }
    }
    
    fun getLastVersion(): Flow<String> = dataStore.data.map { preferences ->
        preferences[LAST_VERSION] ?: "0.0.0"
    }
    
    suspend fun getLastVersionValue(): String {
        return getLastVersion().first()
    }
    
    // Reset all settings
    suspend fun resetSettings() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
    
    // Clear specific settings
    suspend fun clearOnboarding() {
        dataStore.edit { preferences ->
            preferences.remove(ONBOARDING_COMPLETED)
        }
    }
}

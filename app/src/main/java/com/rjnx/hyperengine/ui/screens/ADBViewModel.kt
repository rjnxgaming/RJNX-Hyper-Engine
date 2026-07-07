package com.rjnx.hyperengine.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ADBViewModel @Inject constructor() : ViewModel() {
    
    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()
    
    private val _connectionStatus = MutableStateFlow("Disconnected")
    val connectionStatus = _connectionStatus.asStateFlow()
    
    private val _commandHistory = MutableStateFlow<List<String>>(emptyList())
    val commandHistory = _commandHistory.asStateFlow()
    
    private val _commandOutput = MutableStateFlow<List<String>>(emptyList())
    val commandOutput = _commandOutput.asStateFlow()
    
    fun connectADB() {
        viewModelScope.launch {
            _isConnected.value = true
            _connectionStatus.value = "Connected"
            _commandOutput.value = listOf("ADB connected successfully")
        }
    }
    
    fun disconnectADB() {
        viewModelScope.launch {
            _isConnected.value = false
            _connectionStatus.value = "Disconnected"
            _commandOutput.value = listOf("ADB disconnected")
        }
    }
    
    fun executeCommand(command: String) {
        viewModelScope.launch {
            _commandHistory.value = _commandHistory.value + command
            
            // Simulate command execution
            val output = when {
                command.contains("devices") -> listOf("List of devices attached", "192.168.1.100:5555	device")
                command.contains("version") -> listOf("Android Debug Bridge version 1.0.41", "Version 34.0.4-10434362")
                command.contains("install") -> listOf("Performing Streamed Install", "Success")
                else -> listOf("Executing: $command", "Command executed successfully")
            }
            
            _commandOutput.value = _commandOutput.value + output
        }
    }
    
    fun clearHistory() {
        viewModelScope.launch {
            _commandHistory.value = emptyList()
        }
    }
    
    fun clearOutput() {
        viewModelScope.launch {
            _commandOutput.value = emptyList()
        }
    }
    
    fun connectWireless(ip: String, port: String) {
        viewModelScope.launch {
            _connectionStatus.value = "Connecting to $ip:$port..."
            // Simulate connection
            _isConnected.value = true
            _connectionStatus.value = "Connected (Wireless)"
            _commandOutput.value = listOf("Connected to $ip:$port")
        }
    }
}

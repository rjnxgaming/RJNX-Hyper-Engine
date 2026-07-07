package com.rjnx.hyperengine.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerformanceViewModel @Inject constructor() : ViewModel() {
    
    private val _cpuUsage = MutableStateFlow(0f)
    val cpuUsage = _cpuUsage.asStateFlow()
    
    private val _memoryUsage = MutableStateFlow(0f)
    val memoryUsage = _memoryUsage.asStateFlow()
    
    private val _gpuUsage = MutableStateFlow(0f)
    val gpuUsage = _gpuUsage.asStateFlow()
    
    private val _batteryTemp = MutableStateFlow(0f)
    val batteryTemp = _batteryTemp.asStateFlow()
    
    private val _cpuDataPoints = MutableStateFlow<List<Float>>(emptyList())
    val cpuDataPoints = _cpuDataPoints.asStateFlow()
    
    private val _memoryDataPoints = MutableStateFlow<List<Float>>(emptyList())
    val memoryDataPoints = _memoryDataPoints.asStateFlow()
    
    private val _gpuDataPoints = MutableStateFlow<List<Float>>(emptyList())
    val gpuDataPoints = _gpuDataPoints.asStateFlow()
    
    init {
        startMonitoring()
    }
    
    private fun startMonitoring() {
        viewModelScope.launch {
            while (true) {
                // Simulate real-time data
                _cpuUsage.value = (0.5f..0.8f).random()
                _memoryUsage.value = (0.4f..0.6f).random()
                _gpuUsage.value = (0.3f..0.5f).random()
                _batteryTemp.value = (70f..90f).random()
                
                // Update data points for charts
                updateDataPoints()
                
                delay(1000)
            }
        }
    }
    
    private fun updateDataPoints() {
        viewModelScope.launch {
            val newCpuPoint = _cpuUsage.value
            val newMemoryPoint = _memoryUsage.value
            val newGpuPoint = _gpuUsage.value
            
            _cpuDataPoints.value = (_cpuDataPoints.value + newCpuPoint).takeLast(8)
            _memoryDataPoints.value = (_memoryDataPoints.value + newMemoryPoint).takeLast(8)
            _gpuDataPoints.value = (_gpuDataPoints.value + newGpuPoint).takeLast(8)
        }
    }
    
    fun optimizeCPU() {
        viewModelScope.launch {
            // Simulate CPU optimization
            _cpuUsage.value = 0.4f
            delay(5000)
            _cpuUsage.value = (0.5f..0.8f).random()
        }
    }
    
    fun cleanMemory() {
        viewModelScope.launch {
            // Simulate memory cleaning
            _memoryUsage.value = 0.3f
            delay(5000)
            _memoryUsage.value = (0.4f..0.6f).random()
        }
    }
    
    fun coolDown() {
        viewModelScope.launch {
            // Simulate cooling
            _batteryTemp.value = 60f
            delay(5000)
            _batteryTemp.value = (70f..90f).random()
        }
    }
}

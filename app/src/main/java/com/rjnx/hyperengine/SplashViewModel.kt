package com.rjnx.hyperengine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _splashFinished = MutableStateFlow(false)
    val splashFinished = _splashFinished.asStateFlow()

    init {
        viewModelScope.launch {
            // Simulate initialization
            delay(2000)
            _splashFinished.value = true
        }
    }

    fun onSplashFinished() {
        viewModelScope.launch {
            _splashFinished.value = true
        }
    }
}

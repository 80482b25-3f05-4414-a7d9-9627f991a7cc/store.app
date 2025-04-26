package com.example.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel()
{
    private val _isReady = MutableLiveData(false)
    val isReady: LiveData<Boolean> = _isReady

    init {
        // Simular carga inicial de 3 segundos
        viewModelScope.launch {
            delay(3000)
            _isReady.value = true
        }
    }
}

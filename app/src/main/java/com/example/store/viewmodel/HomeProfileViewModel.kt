package com.example.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeProfileViewModel : ViewModel()
{
    private val _text = MutableLiveData<String>().apply {
        value = "Mi perfil"
    }
    val text: LiveData<String> = _text
}

package com.example.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeOrdersViewModel : ViewModel()
{
    private val _text = MutableLiveData<String>().apply {
        value = "Mis pedidos"
    }
    val text: LiveData<String> = _text
}

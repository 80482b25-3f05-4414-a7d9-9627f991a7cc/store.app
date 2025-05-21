package com.example.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeOurStoresViewModel : ViewModel()
{
    private val _text = MutableLiveData<String>().apply {
        value = "Nuestras tiendas"
    }
    val text: LiveData<String> = _text
}

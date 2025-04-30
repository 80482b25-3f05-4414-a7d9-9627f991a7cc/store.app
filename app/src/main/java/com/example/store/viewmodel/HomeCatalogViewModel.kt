package com.example.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeCatalogViewModel : ViewModel()
{
    private val _text = MutableLiveData<String>().apply {
        value = "Catálogo"
    }
    val text: LiveData<String> = _text
}

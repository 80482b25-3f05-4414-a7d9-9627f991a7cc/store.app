package com.example.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.store.data.repository.ProductRepository
import com.example.store.model.ProductModel

class HomeCatalogViewModel : ViewModel()
{
    private val _products = MutableLiveData<MutableList<ProductModel>>(mutableListOf())
    val products: LiveData<MutableList<ProductModel>> get() = _products

    // Cargar más productos desde el repositorio y agregarlos a la lista de productos
    fun loadMoreProducts() {
        val new = ProductRepository.retrieve()
        _products.value?.addAll(new)
        _products.postValue(_products.value)
    }
}

package com.example.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.store.model.Product

class HomeViewModel : ViewModel() {

    private val _products = MutableLiveData<MutableList<Product>>(mutableListOf())
    val products: LiveData<MutableList<Product>> = _products

    init {
        loadInitialProducts()
    }

    private fun loadInitialProducts() {
        // Crear una lista de productos con valores de ejemplo
        val initialProducts = List(20) {
            Product(name = "Producto #${it + 1}", price = (10 + it * 5).toDouble())
        }
        _products.value = initialProducts.toMutableList()
    }

    fun loadMoreProducts() {
        // Añadir más productos a la lista
        val moreProducts = List(10) {
            Product(name = "Producto nuevo #${(_products.value?.size ?: 0) + it + 1}", price = 50.0)
        }
        _products.value?.addAll(moreProducts)
        _products.postValue(_products.value) // Notificar a los observadores
    }
}

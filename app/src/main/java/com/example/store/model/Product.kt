package com.example.store.model

data class Product(
    val name: String,
    val price: Double,
    var quantity: Int = 0
)

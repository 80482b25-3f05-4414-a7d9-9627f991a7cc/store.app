package com.example.store.data

import com.example.store.R
import com.example.store.model.ProductModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object ProductRepository
{
    @OptIn(ExperimentalUuidApi::class)
    private val baseProduct = ProductModel(
        sku = Uuid.random().toString(),
        description = "Bizcocho de chocolate, pasta de avellanas, cerezas y crema batida.",
        image = R.drawable.ic_menu_camera,
        name = "PASTEL DE CHOCOLATE",
        price = 2300.0
    )

    // Retorna una nueva lista de 10 productos
    fun retrieve(quantity: Int = 10): List<ProductModel>
    {
        return List(quantity) { baseProduct.copy() }
    }
}

package com.example.store.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.store.model.ProductModel

class ProductDiffCallback : DiffUtil.ItemCallback<ProductModel>()
{
    // Compara si los dos productos son el mismo, basado en su nombre
    override fun areItemsTheSame(old: ProductModel, new: ProductModel): Boolean
    {
        return old.name == new.name
    }

    // Compara si los contenidos de los dos productos son iguales
    override fun areContentsTheSame(old: ProductModel, new: ProductModel): Boolean
    {
        return old == new
    }
}

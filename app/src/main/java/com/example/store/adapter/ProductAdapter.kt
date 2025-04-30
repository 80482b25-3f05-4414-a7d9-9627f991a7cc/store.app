package com.example.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.store.R
import com.example.store.model.ProductModel
import com.example.store.ui.viewholder.ProductViewHolder

class ProductAdapter : ListAdapter<ProductModel, ProductViewHolder>(ProductDiffCallback())
{
    // Inflar el layout del producto y retorna el ViewHolder correspondiente
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder
    {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_home_catalog_item, parent, false)
        return ProductViewHolder(view)
    }

    // Asocia los datos del producto con la vista del ViewHolder en la posición correspondiente
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int)
    {
        holder.bind(getItem(position))
    }
}

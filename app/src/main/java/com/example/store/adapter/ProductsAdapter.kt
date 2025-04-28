package com.example.store.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.store.R
import com.example.store.model.Product

class ProductsAdapter(
    private val onQuantityChanged: (Product, Int) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    private val products = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    fun submitList(newList: List<Product>) {
        products.clear()
        products.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textProductName: TextView = view.findViewById(R.id.textProductName)
        private val textProductPrice: TextView = view.findViewById(R.id.textProductPrice)
        private val textQuantity: TextView = view.findViewById(R.id.textQuantity)
        private val btnIncrease: View = view.findViewById(R.id.btnIncrease)
        private val btnDecrease: View = view.findViewById(R.id.btnDecrease)

        fun bind(product: Product) {
            textProductName.text = product.name
            textProductPrice.text = "$${product.price}"
            textQuantity.text = product.quantity.toString()

            btnIncrease.setOnClickListener {
                product.quantity++
                textQuantity.text = product.quantity.toString()
                onQuantityChanged(product, product.quantity)
            }

            btnDecrease.setOnClickListener {
                if (product.quantity > 0) {
                    product.quantity--
                    textQuantity.text = product.quantity.toString()
                    onQuantityChanged(product, product.quantity)
                }
            }
        }
    }
}


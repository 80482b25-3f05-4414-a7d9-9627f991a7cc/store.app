package com.example.store.ui.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.store.model.ProductModel
import com.example.store.R

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    // Vincula los datos de un producto con las vistas correspondientes del item
    fun bind(product: ProductModel)
    {
        itemView.findViewById<TextView>(R.id.productDescriptionTextView).text = product.description
        itemView.findViewById<ImageView>(R.id.productImageView).setImageResource(product.image)
        itemView.findViewById<TextView>(R.id.productNameTextView).text = product.name
        itemView.findViewById<TextView>(R.id.productPriceTextView).text = itemView.context.getString(R.string.home_product_price, product.price.toString())
    }
}

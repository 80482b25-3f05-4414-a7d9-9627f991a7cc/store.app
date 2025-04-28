package com.example.store.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.store.R
import com.example.store.adapter.ProductsAdapter
import com.example.store.databinding.ActivityHomeBinding
import com.example.store.model.Product
import com.example.store.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    // Actualizamos la instancia del adaptador pasando la lambda para manejar el cambio de cantidad
    private val adapter = ProductsAdapter { product, newQuantity ->
        // Aquí puedes manejar lo que sucede cuando la cantidad cambia
        // Por ejemplo, si quieres mostrar un log:
        Log.d("HomeActivity", "Cantidad de ${product.name} cambiada a $newQuantity")

        // O si quieres actualizar el carrito, puedes hacerlo aquí.
        // Si tienes una lista de productos en el carrito, actualiza la cantidad aquí.
        updateCart(product)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_logout -> {
                    finish() // Cerrar sesión y cerrar la actividad
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewProducts.adapter = adapter

        binding.recyclerViewProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) { // Detecta cuando llegamos al fondo
                    homeViewModel.loadMoreProducts()
                }
            }
        })
    }

    private fun setupObservers() {
        homeViewModel.products.observe(this, Observer { products ->
            adapter.submitList(products.toList()) // Importante: enviar una copia para forzar actualización
        })
    }

    private fun setupListeners() {
        binding.fabCart.setOnClickListener {
            // Lógica para ir al carrito
            // startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun updateCart(product: Product) {
        // Aquí implementas la lógica de lo que ocurre cuando la cantidad cambia
        // Por ejemplo, puedes agregar el producto a un carrito global o en memoria.
        // Esto es solo un ejemplo de cómo podrías manejarlo.
        Log.d("HomeActivity", "Producto añadido al carrito: ${product.name} con cantidad: ${product.quantity}")
    }
}


package com.example.store.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.store.adapter.ProductAdapter
import com.example.store.R
import com.example.store.viewmodel.HomeCatalogViewModel

class HomeCatalogFragment : Fragment()
{
    private val viewModel: HomeCatalogViewModel by viewModels()
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View
    {
        val view = inflater.inflate(R.layout.fragment_home_catalog, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.productsRecycler)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ProductAdapter()
        recyclerView.adapter = adapter

        // Scroll infinito, cargar más productos cuando se llega al final de la pantalla
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener()
        {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int)
            {
                super.onScrolled(rv, dx, dy)
                val layoutManager = rv.layoutManager as LinearLayoutManager

                if (layoutManager.findLastVisibleItemPosition() == adapter.itemCount - 1)
                {
                    viewModel.loadMoreProducts()
                }
            }
        })

        // Observar cambios en la lista de productos del ViewModel
        viewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.submitList(products.toList())
        }

        // Inicializar la carga de productos
        viewModel.loadMoreProducts()
        return view
    }
}

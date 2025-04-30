package com.example.store.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.store.databinding.FragmentHomeOrdersBinding
import com.example.store.viewmodel.HomeOrdersViewModel

class HomeOrdersFragment : Fragment()
{
    private var _binding: FragmentHomeOrdersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View
    {
        val homeOrdersViewModel = ViewModelProvider(this)[HomeOrdersViewModel::class.java]

        _binding = FragmentHomeOrdersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.ordersTextView

        homeOrdersViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return root
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}

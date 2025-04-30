package com.example.store.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.store.databinding.FragmentHomeProfileBinding
import com.example.store.viewmodel.HomeProfileViewModel

class HomeProfileFragment : Fragment()
{
    private var _binding: FragmentHomeProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View
    {
        val homeProfileViewModel = ViewModelProvider(this)[HomeProfileViewModel::class.java]

        _binding = FragmentHomeProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.profileTextView

        homeProfileViewModel.text.observe(viewLifecycleOwner) {
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

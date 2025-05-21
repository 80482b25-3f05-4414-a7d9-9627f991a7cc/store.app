package com.example.store.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class HomeOurStoresViewModel : ViewModel()
{
    private val _randomPoints = MutableLiveData<List<LatLng>>()
    val randomPoints: LiveData<List<LatLng>> = _randomPoints

    init {
        generateRandomPoints()
    }

    private fun generateRandomPoints()
    {
        // Bogotá
        val baseLat = 4.6097
        val baseLng = -74.0817

        // Lugares cercanos a Bogotá
        val points = List(40)
        {
            LatLng(
                baseLat + (Math.random() - 0.5) * 0.1,
                baseLng + (Math.random() - 0.5) * 0.1
            )
        }
        _randomPoints.value = points
    }
}

package com.example.store.ui.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.store.R
import com.example.store.viewmodel.HomeOurStoresViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.MarkerOptions

class HomeOurStoresFragment : Fragment(), OnMapReadyCallback
{
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private val viewModel: HomeOurStoresViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home_our_stores, container, false)

        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return view
    }

    override fun onMapReady(map: GoogleMap)
    {
        googleMap = map

        // Habilita el botón de "mi ubicación"
        googleMap.uiSettings.isMyLocationButtonEnabled = true

        // Verifica permisos antes de activar la ubicación
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            googleMap.isMyLocationEnabled = true
        }
        else
        {
            // Pide el permiso si no está concedido
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1001
            )
        }

        // Marcadores aleatorios
        viewModel.randomPoints.observe(viewLifecycleOwner) { points ->
            for (point in points)
            {
                googleMap.addMarker(MarkerOptions().position(point).title("Punto físico store.app"))
            }

            // Centra la cámara en el primer punto
            if (points.isNotEmpty())
            {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(points.first(), 12f))
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                googleMap.isMyLocationEnabled = true
            }
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onPause()
    {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume()
    {
        super.onResume()
        mapView.onResume()
    }
}

package com.example.store

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.store.viewmodel.SplashViewModel

class MainActivity : AppCompatActivity()
{
    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        // Instala la splash screen
        val splashScreen = installSplashScreen()

        // Inicializa el view model de la splash screen
        splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        // Muestra la splash screen
        splashScreen.setKeepOnScreenCondition {
            splashViewModel.isReady.value == false
        }

        // Inicializar la main activity
        super.onCreate(savedInstanceState)

        // Cargar el layout para la main activity
        setContentView(R.layout.activity_main)
    }
}

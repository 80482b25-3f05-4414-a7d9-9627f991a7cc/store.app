package com.example.store.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.store.R
import com.example.store.viewmodel.SplashViewModel

class LoginActivity : AppCompatActivity()
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

        // Inicializar la login activity
        super.onCreate(savedInstanceState)

        // Cargar el layout para la login activity
        setContentView(R.layout.activity_login)
    }
}

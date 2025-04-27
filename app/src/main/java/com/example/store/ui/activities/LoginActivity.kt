package com.example.store.ui.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.store.R
import com.example.store.viewmodel.LoginViewModel
import com.example.store.viewmodel.SplashViewModel
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity()
{
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        // Instala la splash screen
        val splashScreen = installSplashScreen()

        // Inicializa el view model de la login activity
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

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

        // Inicializar los elementos gráficos
        val forgotPasswordTextView = findViewById<TextView>(R.id.forgotPasswordTextView)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val passwordInputLayout = findViewById<TextInputLayout>(R.id.passwordInputLayout)
        val registerTextView = findViewById<TextView>(R.id.registerTextView)
        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val usernameInputLayout = findViewById<TextInputLayout>(R.id.usernameInputLayout)

        // Observa el estado del formulario para habilitar o deshabilitar el botón de iniciar sesión
        loginViewModel.isFormValid.observe(this, Observer { valid ->
            loginButton.isEnabled = valid
        })

        // Observa el estado del campo de la contraseña para mostrar los errores
        loginViewModel.isPasswordValid.observe(this, Observer { valid ->
            if (valid == null || valid.first) {
                passwordInputLayout.error = null
                passwordInputLayout.isErrorEnabled = false
            } else {
                passwordInputLayout.error = valid.second
            }
        })

        // Observa el estado del campo del usuario para mostrar los errores
        loginViewModel.isUsernameValid.observe(this, Observer { valid ->
            if (valid == null || valid.first) {
                usernameInputLayout.error = null
                usernameInputLayout.isErrorEnabled = false
            } else {
                usernameInputLayout.error = valid.second
            }
        })

        // Escucha los cambios en el campo usuario
        usernameEditText.addTextChangedListener(object : TextWatcher
        {
            override fun afterTextChanged(username: Editable?)
            {
                loginViewModel.onUsernameChanged(username.toString())
            }

            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) { }

            override fun onTextChanged(
                s: CharSequence?, start: Int, before: Int, count: Int
            ) { }
        })

        // Escucha los cambios en el campo contraseña
        passwordEditText.addTextChangedListener(object : TextWatcher
        {
            override fun afterTextChanged(password: Editable?)
            {
                loginViewModel.onPasswordChanged(password.toString())
            }

            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) { }

            override fun onTextChanged(
                s: CharSequence?, start: Int, before: Int, count: Int
            ) { }
        })

        // Iniciar sesión y redirigir a la actividad inicial
        loginButton.setOnClickListener()
        {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (loginViewModel.validateLogin(username, password))
            {
                Toast.makeText(this, "Bienvenido $username", Toast.LENGTH_SHORT).show()
                // startActivity(Intent(this, HomeActivity::class.java))
                // finish()
            }
            else
            {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        // Redirigir a la actividad de registrar nuevo usuario
        registerTextView.setOnClickListener()
        {
            Toast.makeText(this, "Abrir: registro", Toast.LENGTH_SHORT).show()
            // startActivity(Intent(this, RegisterActivity::class.java))
            // finish()
        }

        // Redirigir a la actividad de recuperar contraseña
        forgotPasswordTextView.setOnClickListener()
        {
            Toast.makeText(this, "Abrir: recuperar contraseña", Toast.LENGTH_SHORT).show()
            // startActivity(Intent(this, ForgotPasswordActivity::class.java))
            // finish()
        }
    }
}

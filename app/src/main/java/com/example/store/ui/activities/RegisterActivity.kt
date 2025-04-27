package com.example.store.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.store.R
import com.example.store.viewmodel.RegisterViewModel
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity()
{
    // Elementos gráficos
    private lateinit var registerButton: Button
    private lateinit var passwordEditText: EditText
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var usernameEditText: EditText
    private lateinit var usernameInputLayout: TextInputLayout

    // View models
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        // Inicializa el view model de la register activity
        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        // Inicializar la register activity
        super.onCreate(savedInstanceState)

        // Cargar el layout para la register activity
        setContentView(R.layout.activity_register)

        // Inicializar los elementos gráficos
        registerButton = findViewById<Button>(R.id.registerButton)
        passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        passwordInputLayout = findViewById<TextInputLayout>(R.id.passwordInputLayout)
        usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        usernameInputLayout = findViewById<TextInputLayout>(R.id.usernameInputLayout)

        // Configurar los listeners
        setupListeners()

        // Configurar los observers
        setupObservers()
    }

    private fun setupListeners()
    {
        // Iniciar sesión y redirigir a la actividad inicial
        registerButton.setOnClickListener()
        {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (registerViewModel.validateIfUserExists(username = username))
            {
                Toast.makeText(this, "Ya estás registrad@", Toast.LENGTH_SHORT).show()
            }
            else
            {
                registerViewModel.register(username, password)
                Toast.makeText(
                    this, "Bienvenid@ $username!. Ya puedes iniciar sesión.", Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        // Escucha los cambios en el campo contraseña
        passwordEditText.addTextChangedListener(object : TextWatcher
        {
            override fun afterTextChanged(password: Editable?)
            {
                registerViewModel.onPasswordChanged(password.toString())
            }

            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) { }

            override fun onTextChanged(
                s: CharSequence?, start: Int, before: Int, count: Int
            ) { }
        })

        // Escucha los cambios en el campo usuario
        usernameEditText.addTextChangedListener(object : TextWatcher
        {
            override fun afterTextChanged(username: Editable?)
            {
                registerViewModel.onUsernameChanged(username.toString())
            }

            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) { }

            override fun onTextChanged(
                s: CharSequence?, start: Int, before: Int, count: Int
            ) { }
        })
    }

    private fun setupObservers()
    {
        // Observa el estado del formulario para habilitar o deshabilitar el botón de iniciar sesión
        registerViewModel.isFormValid.observe(this, Observer { valid ->
            registerButton.isEnabled = valid
        })

        // Observa el estado del campo de la contraseña para mostrar los errores
        registerViewModel.isPasswordValid.observe(this, Observer { valid ->
            if (valid == null || valid.first) {
                passwordInputLayout.error = null
                passwordInputLayout.isErrorEnabled = false
            } else {
                passwordInputLayout.error = valid.second
            }
        })

        // Observa el estado del campo del usuario para mostrar los errores
        registerViewModel.isUsernameValid.observe(this, Observer { valid ->
            if (valid == null || valid.first) {
                usernameInputLayout.error = null
                usernameInputLayout.isErrorEnabled = false
            } else {
                usernameInputLayout.error = valid.second
            }
        })
    }
}

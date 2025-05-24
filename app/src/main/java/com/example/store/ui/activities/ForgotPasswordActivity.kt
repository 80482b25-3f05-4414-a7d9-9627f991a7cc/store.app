package com.example.store.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.store.R
import com.example.store.data.repository.UserRepository
import com.example.store.database.StoreDatabase
import com.example.store.viewmodel.ForgotPasswordViewModel
import com.example.store.viewmodel.GenericViewModelFactory
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class ForgotPasswordActivity : AppCompatActivity()
{
    // Elementos gráficos
    private lateinit var resetPasswordButton: Button
    private lateinit var usernameEditText: EditText
    private lateinit var usernameInputLayout: TextInputLayout

    // View models
    private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        // Inicializa el view model de la forgot password activity
        val db = StoreDatabase.getDatabase(applicationContext)
        val userDAO = db.userDao()
        val repository = UserRepository(userDAO)
        val factory = GenericViewModelFactory { ForgotPasswordViewModel(repository) }
        forgotPasswordViewModel = ViewModelProvider(this, factory)[ForgotPasswordViewModel::class.java]

        // Inicializar la forgot password activity
        super.onCreate(savedInstanceState)

        // Cargar el layout para la forgot password activity
        setContentView(R.layout.activity_forgot_password)

        // Inicializar los elementos gráficos
        resetPasswordButton = findViewById(R.id.resetPasswordButton)
        usernameEditText = findViewById(R.id.usernameEditText)
        usernameInputLayout = findViewById(R.id.usernameInputLayout)

        // Configurar los listeners
        setupListeners()

        // Configurar los observers
        setupObservers()
    }

    private fun setupListeners()
    {
        // Recuperar contraseña y redirigir a la actividad inicial
        resetPasswordButton.setOnClickListener()
        {
            val username = usernameEditText.text.toString().trim()

            lifecycleScope.launch {
                if (forgotPasswordViewModel.validateIfUserExists(username = username))
                {
                    forgotPasswordViewModel.recoverPassword(username = username)
                }

                Toast.makeText(
                    this@ForgotPasswordActivity, "Te enviamos un correo de recuperación.", Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this@ForgotPasswordActivity, LoginActivity::class.java))
                finish()
            }
        }

        // Escucha los cambios en el campo usuario
        usernameEditText.addTextChangedListener(object : TextWatcher
        {
            override fun afterTextChanged(username: Editable?)
            {
                forgotPasswordViewModel.onUsernameChanged(username.toString())
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
        forgotPasswordViewModel.isFormValid.observe(this) { valid ->
            resetPasswordButton.isEnabled = valid
        }

        // Observa el estado del campo del usuario para mostrar los errores
        forgotPasswordViewModel.isUsernameValid.observe(this) { valid ->
            if (valid == null || valid.first) {
                usernameInputLayout.error = null
                usernameInputLayout.isErrorEnabled = false
            } else {
                usernameInputLayout.error = valid.second
            }
        }
    }
}

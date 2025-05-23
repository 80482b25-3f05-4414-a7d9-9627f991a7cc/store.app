package com.example.store.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.store.R
import com.example.store.data.repository.UserRepository
import com.example.store.database.StoreDatabase
import com.example.store.viewmodel.GenericViewModelFactory
import com.example.store.viewmodel.LoginViewModel
import com.example.store.viewmodel.SplashViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity()
{
    // Elementos gráficos
    private lateinit var forgotPasswordTextView: TextView
    private lateinit var loginButton: Button
    private lateinit var loginWithGoogleButton: Button
    private lateinit var passwordEditText: EditText
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var registerTextView: TextView
    private lateinit var usernameEditText: EditText
    private lateinit var usernameInputLayout: TextInputLayout

    // View models
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var splashViewModel: SplashViewModel

    // Cliente de Google
    private lateinit var googleSignInClient: GoogleSignInClient

    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try
        {
            val account = task.getResult(ApiException::class.java)
            account?.idToken?.let { idToken ->
                loginViewModel.loginWithGoogle(idToken)
            }
        }
        catch(e: ApiException)
        {
            Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        // Instala la splash screen
        val splashScreen = installSplashScreen()

        // Inicializa el view model de la login activity
        val db = StoreDatabase.getDatabase(applicationContext)
        val userDAO = db.userDao()
        val repository = UserRepository(userDAO)
        val factory = GenericViewModelFactory { LoginViewModel(repository) }
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

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
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView)
        loginButton = findViewById(R.id.loginButton)
        loginWithGoogleButton = findViewById<Button>(R.id.loginWithGoogleButton)
        passwordEditText = findViewById(R.id.passwordEditText)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)
        registerTextView = findViewById(R.id.registerTextView)
        usernameEditText = findViewById(R.id.usernameEditText)
        usernameInputLayout = findViewById(R.id.usernameInputLayout)

        // Configurar los listeners
        setupListeners()

        // Configurar los observers
        setupObservers()

        // Inicio de sesión con Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.login_with_google_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun setupListeners()
    {
        // Redirigir a la actividad de recuperar contraseña
        forgotPasswordTextView.setOnClickListener()
        {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
            finish()
        }

        // Iniciar sesión y redirigir a la actividad inicial
        loginButton.setOnClickListener()
        {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            lifecycleScope.launch {
                if (loginViewModel.validateLogin(username, password))
                {
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                }
                else
                {
                    Toast.makeText(this@LoginActivity, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }

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

        // Redirigir a la actividad de registrar nuevo usuario
        registerTextView.setOnClickListener()
        {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

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

        // Iniciar sesión con Google
        loginWithGoogleButton.setOnClickListener {
            signInLauncher.launch(googleSignInClient.signInIntent)
        }
    }

    private fun setupObservers()
    {
        // Observa el estado del formulario para habilitar o deshabilitar el botón de iniciar sesión
        loginViewModel.isFormValid.observe(this) { valid ->
            loginButton.isEnabled = valid
        }

        // Observa el estado del campo de la contraseña para mostrar los errores
        loginViewModel.isPasswordValid.observe(this) { valid ->
            if (valid == null || valid.first) {
                passwordInputLayout.error = null
                passwordInputLayout.isErrorEnabled = false
            } else {
                passwordInputLayout.error = valid.second
            }
        }

        // Observa el estado del campo del usuario para mostrar los errores
        loginViewModel.isUsernameValid.observe(this) { valid ->
            if (valid == null || valid.first) {
                usernameInputLayout.error = null
                usernameInputLayout.isErrorEnabled = false
            } else {
                usernameInputLayout.error = valid.second
            }
        }

        // Inicio de sesión con Google
        loginViewModel.googleSignInResult.observe(this) { result ->
            result
                .onSuccess { _ ->
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }.onFailure {
                    Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                }
        }
    }
}

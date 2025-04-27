package com.example.store.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.store.data.UserRepository

class ForgotPasswordViewModel : ViewModel()
{
    // LiveData para habilitar o deshabilitar el botón de recuperar contraseña
    private val _isFormValid = MutableLiveData(false)
    val isFormValid: LiveData<Boolean> = _isFormValid

    // LiveData para mostrar errores en el campo del usuario
    private val _isUsernameValid = MutableLiveData<Pair<Boolean, String>?>(null)
    val isUsernameValid: LiveData<Pair<Boolean, String>?> = _isUsernameValid

    // Variables para guardar temporalmente lo que el usuario va escribiendo
    private var currentUsername: String = ""

    // Validar campo y formulario en cada cambio que sufre el usuario
    fun onUsernameChanged(username: String)
    {
        currentUsername = username
        validateUsername()
        validateForm()
    }

    // Validar el estado del formulario
    private fun validateForm()
    {
        _isFormValid.value = _isUsernameValid.value?.first == true
    }

    // Validar el estado del usuario
    private fun validateUsername()
    {
        if (currentUsername.isBlank() && currentUsername.isEmpty())
        {
            _isUsernameValid.value = false to "El correo no puede estar vacío"
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(currentUsername).matches())
        {
            _isUsernameValid.value = false to "Correo no válido"
        }
        else
        {
            _isUsernameValid.value = true to "Validación exitosa"
        }
    }

    // Validar si el usuario ya existe
    fun validateIfUserExists(username: String): Boolean
    {
        return UserRepository.existsUserByEmail(
            username = username
        )
    }

    // Simular la recuperación de la contraseña
    fun recoverPassword(username: String)
    {
        return UserRepository.resetPassword(username = username)
    }
}

package com.example.store.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.store.data.UserRepository
import com.example.store.model.UserModel

class RegisterViewModel : ViewModel()
{
    // LiveData para habilitar o deshabilitar el botón de registro
    private val _isFormValid = MutableLiveData(false)
    val isFormValid: LiveData<Boolean> = _isFormValid

    // LiveData para mostrar errores en el campo de la contraseña
    private val _isPasswordValid = MutableLiveData<Pair<Boolean, String>?>(null)
    val isPasswordValid: LiveData<Pair<Boolean, String>?> = _isPasswordValid

    // LiveData para mostrar errores en el campo del usuario
    private val _isUsernameValid = MutableLiveData<Pair<Boolean, String>?>(null)
    val isUsernameValid: LiveData<Pair<Boolean, String>?> = _isUsernameValid

    // Variables para guardar temporalmente lo que el usuario va escribiendo
    private var currentUsername: String = ""
    private var currentPassword: String = ""

    // Validar campo y formulario en cada cambio que sufre la contraseña
    fun onPasswordChanged(password: String)
    {
        currentPassword = password
        validatePassword()
        validateForm()
    }

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
        _isFormValid.value = _isPasswordValid.value?.first == true
                && _isUsernameValid.value?.first == true
    }

    // Validar el estado de la contraseña
    private fun validatePassword()
    {
        if (currentPassword.isBlank() && currentPassword.isEmpty())
        {
            _isPasswordValid.value = false to "La contraseña no puede estar vacía"
        }
        else if (currentPassword.length < PASSWORD_MINIMUM_LENGTH)
        {
            _isPasswordValid.value = false to "Contraseña no válida"
        }
        else
        {
            _isPasswordValid.value = true to "Validación exitosa"
        }
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

    // Simular la inserción del usuario en la base de datos
    fun register(username: String, password: String)
    {
        return UserRepository.addUser(
            UserModel(username = username, password = password)
        )
    }

    companion object {
        // Mínima longitud de la contraseña
        private const val PASSWORD_MINIMUM_LENGTH: Int = 6
    }
}

package com.example.store.data

import com.example.store.model.UserModel

object UserRepository
{
    private val users = mutableListOf<UserModel>()

    // Agregar un nuevo usuario
    fun addUser(user: UserModel)
    {
        users.add(user)
    }

    // Existe un usuario por correo
    fun existsUserByEmail(username: String): Boolean
    {
        return users.any {
            it.username == username
        }
    }

    // Existe un usuario por correo y contraseña
    fun existsUserByEmailAndPassword(username: String, password: String): Boolean
    {
        return users.any {
            it.username == username && it.password == password
        }
    }

    // Restaurar la contraseña
    fun resetPassword(username: String)
    {
        users.find {
            it.username == username
        }.let {
            it?.password = "000000" // Por ahora quemado
        }
    }
}

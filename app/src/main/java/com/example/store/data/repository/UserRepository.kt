package com.example.store.data.repository

import com.example.store.data.dao.UserDAO
import com.example.store.model.UserModel

class UserRepository(private val userDAO: UserDAO)
{
    // Retornar todos los usuarios
    val users = userDAO.retrieveAll()

    // Agregar un nuevo usuario
    suspend fun addUser(userModel: UserModel)
    {
        userDAO.insert(userModel)
    }

    // Eliminar un usuario
    suspend fun deleteUser(userModel: UserModel)
    {
        userDAO.delete(userModel)
    }

    // Existe un usuario por correo
    suspend fun existsUserByEmail(username: String): Boolean
    {
        return userDAO.retrieveByUsername(username)
    }

    // Existe un usuario por correo y contraseña
    suspend fun existsUserByEmailAndPassword(username: String, password: String): Boolean
    {
        return userDAO.retrieveByUsernameAndPassword(username, password)
    }

    // Restaurar la contraseña
    suspend fun resetPassword(username: String)
    {
        userDAO.resetPassword(username, "000000")
    }
}

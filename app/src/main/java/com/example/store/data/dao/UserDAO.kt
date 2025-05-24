package com.example.store.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.store.model.UserModel

@Dao
interface UserDAO
{
    @Delete
    suspend fun delete(userModel: UserModel)

    @Query("select exists(select 1 from users where username = :username)")
    suspend fun retrieveByUsername(username: String): Boolean

    @Query("select exists(select 1 from users where username = :username and password = :password)")
    suspend fun retrieveByUsernameAndPassword(username: String, password: String): Boolean

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insert(userModel: UserModel)

    @Query("update users SET password = :password WHERE username = :username")
    suspend fun resetPassword(username: String, password: String)

    @Query("select * from users")
    fun retrieveAll(): LiveData<List<UserModel>>
}

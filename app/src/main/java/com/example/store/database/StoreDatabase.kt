package com.example.store.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.store.data.dao.UserDAO
import com.example.store.model.UserModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [UserModel::class], version = 1)
abstract class StoreDatabase : RoomDatabase()
{
    abstract fun userDao(): UserDAO

    companion object
    {
        @Volatile private var database: StoreDatabase? = null

        fun getDatabase(context: Context): StoreDatabase
        {
            return database ?: synchronized(this)
            {
                Room.databaseBuilder(context.applicationContext, StoreDatabase::class.java, "store_database")
                    .addCallback(InitUsersCallback(context))
                    .build().also { database = it }
            }
        }
    }

    private class InitUsersCallback(private val context: Context) : RoomDatabase.Callback()
    {
        override fun onCreate(db: SupportSQLiteDatabase)
        {
            super.onCreate(db)

            CoroutineScope(Dispatchers.IO).launch {

                val database = getDatabase(context)
                val dao = database.userDao()

                dao.insert(UserModel(
                    username = "admin@store.app",
                    password = "123456",
                    fullName = "Jhon Montero (admin)",
                    role = "admin"
                ))

                dao.insert(UserModel(
                    username = "client@store.app",
                    password = "123456",
                    fullName = "Jhon Montero (client)",
                    role = "client"
                ))
            }
        }
    }
}

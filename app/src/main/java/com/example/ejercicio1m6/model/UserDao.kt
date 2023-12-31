package com.example.ejercicio1m6.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.ejercicio1m6.model.User


@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User?)

    @Delete
    fun deleteUser(user: User?)

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<User>>

}

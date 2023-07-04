package com.example.ejercicio1m6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejercicio1m6.model.AppDatabase
import com.example.ejercicio1m6.model.User
import com.example.ejercicio1m6.model.UserAdapter
import com.example.ejercicio1m6.model.UserDao
import com.example.room.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userDao: UserDao
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDao = AppDatabase.getDatabase(this).userDao()
        userAdapter = UserAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = userAdapter

        binding.button1.setOnClickListener { addUser() }
        binding.button2.setOnClickListener { deleteUser() }
        binding.button3.setOnClickListener { displayAllUsers() }
    }

    private fun addUser() {
        val gameName = binding.editTextGameName.text.toString()
        val fullName = binding.editTextFullName.text.toString()
        val ageText = binding.editTextAge.text.toString()

        if (gameName.isBlank() || fullName.isBlank() || ageText.isBlank()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val age = ageText.toIntOrNull()
        if (age == null) {
            Toast.makeText(this, "La edad debe ser un número válido", Toast.LENGTH_SHORT).show()
            return
        }

        val user = User(gameName = gameName, fullName = fullName, age = age)

        GlobalScope.launch(Dispatchers.IO) {
            userDao.insertUser(user)
        }

        clearFields()
    }

    private fun deleteUser() {
        val allUsers = userAdapter.getAllUsers()

        if (allUsers.isEmpty()) {
            Toast.makeText(this, "No hay usuarios registrados", Toast.LENGTH_SHORT).show()
            return
        }

        val userNames = allUsers.map { it.fullName }.toTypedArray()

        AlertDialog.Builder(this)
            .setTitle("Eliminar usuario")
            .setItems(userNames) { _, which ->
                val user = allUsers[which]
                GlobalScope.launch(Dispatchers.IO) {
                    userDao.deleteUser(user)
                }
            }
            .show()
    }

    private fun displayAllUsers() {
        userDao.getAllUsers().observe(this, { users ->
            userAdapter.setUsers(users)
        })
    }

    private fun clearFields() {
        binding.editTextGameName.text.clear()
        binding.editTextFullName.text.clear()
        binding.editTextAge.text.clear()
    }
}

package com.example.ejercicio1m6.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.room.databinding.ItemUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var users: List<User> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = users.size

    fun setUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    fun getAllUsers(): List<User> = users

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.textViewGameName.text = user.gameName
            binding.textViewFullName.text = user.fullName
            binding.textViewAge.text = user.age.toString()
        }
    }
}

package com.example.logintask.ui.adapters

import android.view.LayoutInflater
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.logintask.data.User
import com.example.logintask.databinding.ItemUserBinding
import android.view.ViewGroup


class UserAdapter : androidx.recyclerview.widget.ListAdapter<User, UserAdapter.UsersViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class UsersViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(user: User){
            binding.apply{
                name.text = user.name
                email.text = user.email
                number.text = user.number.toString()
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }
}
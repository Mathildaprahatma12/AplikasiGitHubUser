package com.example.aplikasigithubuser.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasigithubuser.data.model.UserItem
import com.example.aplikasigithubuser.databinding.ItemUserBinding
import com.example.aplikasigithubuser.utils.UserHelper.loadAvatar

class MainAdapter(private val users: List<UserItem?>, private val onItemClick: (String?) -> Unit = {}) :
    RecyclerView.Adapter<MainAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        users[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = users.size

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserItem) {
            binding.apply {
                tvUsername.text = user.login
                imgAvatar.loadAvatar(itemView, user.avatarUrl)
                root.setOnClickListener { onItemClick(user.login) }
            }
        }
    }
}
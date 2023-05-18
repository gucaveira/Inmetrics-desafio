package com.gustavo.rocha.inmetrics.ui.fragment.listUser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gustavo.rocha.core.domain.modal.UserGitHub
import com.gustavo.rocha.inmetrics.databinding.ItemUserBinding
import com.gustavo.rocha.inmetrics.imageLoader.ImageLoader
import javax.inject.Inject

class UsersListAdapter @Inject constructor(
    private val list: List<UserGitHub>,
    private val imageLoader: ImageLoader,
    private val onItemClickListener: OnItemClick = {},
) : RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        imageLoader.load(holder.imgAvatar, item.avatarUrl.plus(".png"))
        holder.txvName.text = item.login
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgAvatar: ImageView = binding.imgAvatar
        val txvName: TextView = binding.txvName
    }
}

typealias OnItemClick = () -> Unit
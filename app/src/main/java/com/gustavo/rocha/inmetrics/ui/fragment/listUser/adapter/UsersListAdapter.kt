package com.gustavo.rocha.inmetrics.ui.fragment.listUser.adapter

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.gustavo.rocha.core.domain.modal.UserGitHub
import com.gustavo.rocha.inmetrics.imageLoader.ImageLoader
import javax.inject.Inject

class UsersListAdapter @Inject constructor(
    private val imageLoader: ImageLoader,
    private val onItemClickListener: (view: View) -> Unit,
) : PagingDataAdapter<UserGitHub, ViewHolder>(differCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent, imageLoader, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item -> holder.bind(item) }
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<UserGitHub>() {
            override fun areItemsTheSame(oldItem: UserGitHub, newItem: UserGitHub): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserGitHub, newItem: UserGitHub): Boolean {
                return oldItem == newItem
            }
        }
    }
}
package com.gustavo.rocha.inmetrics.ui.fragment.listUser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gustavo.rocha.core.domain.modal.UserGitHub
import com.gustavo.rocha.inmetrics.databinding.ItemUserBinding
import com.gustavo.rocha.inmetrics.imageLoader.ImageLoader

class ViewHolder private constructor(
    binding: ItemUserBinding,
    private val imageLoader: ImageLoader,
    private val onItemClickListener: (view: View) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private val imgAvatar: ImageView = binding.imgAvatar
    private val txvName: TextView = binding.txvName

    fun bind(item: UserGitHub) {
        txvName.text = item.login
        imageLoader.load(imgAvatar, item.avatarUrl.plus(".png"))

        itemView.setOnClickListener {
            onItemClickListener.invoke(it)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            imageLoader: ImageLoader,
            onItemClickListener: (view: View) -> Unit,
        ): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemUserBinding.inflate(inflater, parent, false)
            return ViewHolder(itemBinding, imageLoader, onItemClickListener)
        }
    }
}
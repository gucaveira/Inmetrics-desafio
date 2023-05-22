package com.gustavo.rocha.inmetrics.ui.fragment.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gustavo.rocha.core.domain.modal.ReposUser
import com.gustavo.rocha.inmetrics.databinding.ItemReposBinding

class ReposAdapter(
    private val reposList: List<ReposUser>,
) : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun getItemCount() = reposList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reposList[position])
    }

    class ViewHolder(private val binding: ItemReposBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reposUser: ReposUser) {
            binding.txtNameRepos.text = reposUser.htmlURL
            reposUser.description?.let {
                binding.txtDescription.text = "Descrição: " + "${reposUser.description}"
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
            ): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val itemBinding = ItemReposBinding.inflate(inflater, parent, false)
                return ViewHolder(itemBinding)
            }
        }
    }
}
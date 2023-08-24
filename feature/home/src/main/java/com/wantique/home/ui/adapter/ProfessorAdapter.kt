package com.wantique.home.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.home.R
import com.wantique.home.databinding.ListItemProfessorBinding
import com.wantique.home.domain.model.ProfessorItem
import com.wantique.home.ui.adapter.listener.OnProfessorClickListener

class ProfessorAdapter(
    private val onProfessorClickListener: OnProfessorClickListener
) : ListAdapter<ProfessorItem, ProfessorAdapter.ProfessorViewHolder>(object : DiffUtil.ItemCallback<ProfessorItem>() {
    override fun areItemsTheSame(oldItem: ProfessorItem, newItem: ProfessorItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ProfessorItem, newItem: ProfessorItem): Boolean {
        return oldItem == newItem
    }

}) {
    inner class ProfessorViewHolder(private val binding: ListItemProfessorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProfessorItem) {
            binding.item = item

            if(item.update) {
                binding.root.setOnClickListener { onProfessorClickListener.onClick(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfessorViewHolder {
        val binding = ListItemProfessorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfessorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfessorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
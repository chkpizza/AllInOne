package com.wantique.daily.ui.pastExam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.daily.databinding.ListItemDescriptionBinding
import com.wantique.daily.domain.model.Description

class DescriptionAdapter : ListAdapter<Description, DescriptionAdapter.DescriptionViewHolder>(object : DiffUtil.ItemCallback<Description>() {
    override fun areItemsTheSame(oldItem: Description, newItem: Description): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Description, newItem: Description): Boolean {
        return oldItem == newItem
    }

}) {
    inner class DescriptionViewHolder(private val binding: ListItemDescriptionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Description) {
            binding.pastExamDescriptionTvNumber.text = item.number
            binding.pastExamDescriptionTvDescription.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescriptionViewHolder {
        val binding = ListItemDescriptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DescriptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DescriptionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
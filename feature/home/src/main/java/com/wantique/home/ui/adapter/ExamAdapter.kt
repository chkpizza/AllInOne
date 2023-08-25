package com.wantique.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.home.databinding.ListItemExamBinding
import com.wantique.home.domain.model.ExamItem

class ExamAdapter : ListAdapter<ExamItem, ExamAdapter.ExamViewHolder>(object : DiffUtil.ItemCallback<ExamItem>() {
    override fun areItemsTheSame(oldItem: ExamItem, newItem: ExamItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ExamItem, newItem: ExamItem): Boolean {
        return oldItem == newItem
    }
}) {
    inner class ExamViewHolder(private val binding: ListItemExamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExamItem) {
            binding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        val binding = ListItemExamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
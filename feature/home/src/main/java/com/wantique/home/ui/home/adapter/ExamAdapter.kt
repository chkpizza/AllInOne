package com.wantique.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.home.databinding.ListItemExamBinding
import com.wantique.home.domain.model.ExamPlanItem

class ExamAdapter : ListAdapter<ExamPlanItem, ExamAdapter.ExamViewHolder>(object : DiffUtil.ItemCallback<ExamPlanItem>() {
    override fun areItemsTheSame(oldItem: ExamPlanItem, newItem: ExamPlanItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ExamPlanItem, newItem: ExamPlanItem): Boolean {
        return oldItem == newItem
    }
}) {
    inner class ExamViewHolder(private val binding: ListItemExamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExamPlanItem) {
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
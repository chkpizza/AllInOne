package com.wantique.daily.ui.daily.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.daily.databinding.ListItemPastExamPreviewBinding
import com.wantique.daily.domain.model.PastExam
import com.wantique.daily.ui.daily.adapter.listener.OnPastExamClickListener

class PastExamPreviewAdapter(
    private val onPastExamClickListener: OnPastExamClickListener
) : ListAdapter<PastExam, PastExamPreviewAdapter.PastExamPreviewViewHolder>(object : DiffUtil.ItemCallback<PastExam>() {
    override fun areItemsTheSame(oldItem: PastExam, newItem: PastExam): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PastExam, newItem: PastExam): Boolean {
        return oldItem == newItem
    }
}) {
    inner class PastExamPreviewViewHolder(private val binding: ListItemPastExamPreviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PastExam) {
            binding.pastExamPreviewTvSource.text = item.source

            binding.root.setOnClickListener {
                onPastExamClickListener.onClick(bindingAdapterPosition, currentList)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastExamPreviewViewHolder {
        val binding = ListItemPastExamPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PastExamPreviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PastExamPreviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
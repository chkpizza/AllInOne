package com.wantique.daily.ui.daily.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wantique.daily.databinding.ListItemRecordPreviewBinding
import com.wantique.daily.domain.model.Record

class RecordPreviewAdapter : ListAdapter<Record, RecordPreviewAdapter.RecordPreviewViewHolder>(object : DiffUtil.ItemCallback<Record>() {
    override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
        return oldItem == newItem
    }

}) {
    inner class RecordPreviewViewHolder(private val binding: ListItemRecordPreviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Record) {
            Glide.with(binding.recordPreviewIvImage.context)
                .load(item.imageUrl)
                .into(binding.recordPreviewIvImage)

            binding.recordPreviewTvBody.text = item.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordPreviewViewHolder {
        val binding = ListItemRecordPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordPreviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordPreviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}
package com.wantique.daily.ui.daily.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wantique.daily.databinding.ListItemPromisePreviewBinding
import com.wantique.daily.domain.model.Promise

class PromisePreviewAdapter : ListAdapter<Promise, PromisePreviewAdapter.PromisePreviewViewHolder>(object : DiffUtil.ItemCallback<Promise>() {
    override fun areItemsTheSame(oldItem: Promise, newItem: Promise): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Promise, newItem: Promise): Boolean {
        return oldItem == newItem
    }
}) {
    inner class PromisePreviewViewHolder(private val binding: ListItemPromisePreviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Promise) {
            binding.promisePreviewTvBody.text = item.body
            Glide.with(binding.promisePreviewIvBackground.context)
                .load(item.imageUrl)
                .into(binding.promisePreviewIvBackground)

            binding.root.setOnClickListener {
                Log.d("promisePreviewTest", "$bindingAdapterPosition  / $currentList")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromisePreviewViewHolder {
        val binding = ListItemPromisePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PromisePreviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PromisePreviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
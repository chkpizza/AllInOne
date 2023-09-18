package com.wantique.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.home.databinding.ListItemNoticeBinding
import com.wantique.home.domain.model.NoticeItem
import com.wantique.home.ui.home.adapter.listener.OnNoticeClickListener

class NoticeAdapter(
    private val onNoticeClickListener: OnNoticeClickListener
) : ListAdapter<NoticeItem, NoticeAdapter.NoticeViewHolder>(object : DiffUtil.ItemCallback<NoticeItem>() {
    override fun areItemsTheSame(oldItem: NoticeItem, newItem: NoticeItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NoticeItem, newItem: NoticeItem): Boolean {
        return oldItem == newItem
    }
}) {
    inner class NoticeViewHolder(private val binding: ListItemNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoticeItem) {
            binding.item = item

            binding.root.setOnClickListener {
                onNoticeClickListener.onClick(item.documentId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val binding = ListItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
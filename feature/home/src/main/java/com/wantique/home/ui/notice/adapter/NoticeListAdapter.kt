package com.wantique.home.ui.notice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.home.databinding.ListItemNoticeBinding
import com.wantique.home.domain.model.NoticeItem

class NoticeListAdapter(
    private val onNoticeClickListener: OnNoticeClickListener
) : ListAdapter<NoticeItem, NoticeListAdapter.NoticeListViewHolder>(object : DiffUtil.ItemCallback<NoticeItem>() {
    override fun areItemsTheSame(oldItem: NoticeItem, newItem: NoticeItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NoticeItem, newItem: NoticeItem): Boolean {
        return oldItem == newItem
    }
}) {
    inner class NoticeListViewHolder(private val binding: ListItemNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoticeItem) {
            binding.item = item

            binding.root.setOnClickListener {
                onNoticeClickListener.onClick(item.documentId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeListViewHolder {
        val binding = ListItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticeListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
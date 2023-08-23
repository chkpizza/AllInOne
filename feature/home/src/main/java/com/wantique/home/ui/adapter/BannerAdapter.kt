package com.wantique.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wantique.home.databinding.ListItemBannerBinding
import com.wantique.home.domain.model.BannerItem

class BannerAdapter : ListAdapter<BannerItem, BannerAdapter.BannerViewHolder>(object : DiffUtil.ItemCallback<BannerItem>() {
    override fun areItemsTheSame(oldItem: BannerItem, newItem: BannerItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: BannerItem, newItem: BannerItem): Boolean {
        return oldItem == newItem
    }
}) {
    inner class BannerViewHolder(private val binding: ListItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BannerItem) {
            binding.item = item
            /*
            Glide.with(binding.homeIvBanner.context)
                .load(item.url)
                .into(binding.homeIvBanner)

             */
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = ListItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
package com.wantique.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.home.databinding.ListItemBannerWrapperBinding
import com.wantique.home.databinding.ListItemCategoryWrapperBinding
import com.wantique.home.domain.model.Home
import com.wantique.home.ui.adapter.listener.OnCategoryClickListener

class HomeAdapter(
    private val onCategoryClickListener: OnCategoryClickListener
) : ListAdapter<Home, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<Home>() {
    override fun areItemsTheSame(oldItem: Home, newItem: Home): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Home, newItem: Home): Boolean {
        return oldItem == newItem
    }
}) {
    private val bannerAdapter = BannerAdapter()
    private val categoryAdapter = CategoryAdapter(onCategoryClickListener)

    inner class BannerWrapperViewHolder(private val binding: ListItemBannerWrapperBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Home) {
            (item as Home.Banner).also {
                binding.homeVpBanner.adapter = bannerAdapter
                binding.homeTvNotice.isSelected = true
                binding.homeTvNotice.setHorizontallyScrolling(true)
                binding.item = it
            }
        }
    }

    inner class CategoryWrapperViewHolder(private val binding: ListItemCategoryWrapperBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Home) {
            (item as Home.Category).also {
                binding.homeTvCategoryTitle.text = it.title
                binding.homeRvCategory.adapter = categoryAdapter
                categoryAdapter.submitList(it.categories)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when(getItem(position)) {
        is Home.Banner -> 1000
        is Home.Category -> 2000
        else -> throw RuntimeException()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when(viewType) {
        1000 -> {
            val binding = ListItemBannerWrapperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            BannerWrapperViewHolder(binding)
        }
        2000 -> {
            val binding = ListItemCategoryWrapperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            CategoryWrapperViewHolder(binding)
        }
        else -> throw RuntimeException()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItem(position)) {
            is Home.Banner -> (holder as BannerWrapperViewHolder).bind(getItem(position))
            is Home.Category -> (holder as CategoryWrapperViewHolder).bind(getItem(position))
        }
    }

    fun getCategoryAdapter() = categoryAdapter
}
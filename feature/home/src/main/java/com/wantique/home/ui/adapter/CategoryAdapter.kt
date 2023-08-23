package com.wantique.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.wantique.home.R
import com.wantique.home.databinding.ListItemCategoryBinding
import com.wantique.home.ui.adapter.listener.OnCategoryClickListener

class CategoryAdapter(
    private val onCategoryClickListener: OnCategoryClickListener
) : ListAdapter<String, CategoryAdapter.CategoryViewHolder>(object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}) {
    private var selectedPosition = -1

    inner class CategoryViewHolder(private val binding: ListItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.homeTvCategory.text = item

            (binding.root as MaterialCardView).also {
                if(selectedPosition == bindingAdapterPosition) {
                    it.strokeColor = ContextCompat.getColor(it.context, com.wantique.resource.R.color.colorPrimary)
                    binding.homeTvCategory.setTextColor(ContextCompat.getColor(it.context, com.wantique.resource.R.color.colorPrimary))
                } else {
                    it.strokeColor = ContextCompat.getColor(it.context, com.wantique.resource.R.color.disabledBackground)
                    binding.homeTvCategory.setTextColor(ContextCompat.getColor(it.context, com.wantique.resource.R.color.disabledText))
                }
            }

            binding.root.setOnClickListener {
                onCategoryClickListener.onClick(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ListItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun updateCategoryPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }
}
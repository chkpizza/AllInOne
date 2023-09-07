package com.wantique.daily.ui.record.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.daily.R
import com.wantique.daily.databinding.ListItemReportBinding

class ReportAdapter : ListAdapter<String, ReportAdapter.ReportViewHolder>(object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}) {
    var selectedPosition = -1
    inner class ReportViewHolder(private val binding: ListItemReportBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.reportTvReason.text = item

            if(selectedPosition == bindingAdapterPosition) {
                binding.reportIvSelect.setImageResource(R.drawable.ic_select)
            } else {
                binding.reportIvSelect.setImageResource(R.drawable.ic_unselect)
            }

            binding.root.setOnClickListener {
                selectedPosition = bindingAdapterPosition
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ListItemReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
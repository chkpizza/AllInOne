package com.wantique.daily.ui.record.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wantique.daily.databinding.ListItemTodayRecordBinding
import com.wantique.daily.domain.model.Record
import com.wantique.daily.ui.record.adapter.listener.OnRecordReportClickListener
import com.wantique.firebase.Firebase

class TodayRecordAdapter(
    private val onRecordReportClickListener: OnRecordReportClickListener
) : ListAdapter<Record, TodayRecordAdapter.TodayRecordViewHolder>(object : DiffUtil.ItemCallback<Record>() {
    override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
        return oldItem == newItem
    }
}) {
    inner class TodayRecordViewHolder(private val binding: ListItemTodayRecordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Record) {
            binding.item = item

            binding.todayRecordIvReport.setOnClickListener {
                onRecordReportClickListener.onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayRecordViewHolder {
        val binding = ListItemTodayRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodayRecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodayRecordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
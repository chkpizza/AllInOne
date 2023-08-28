package com.wantique.home.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.home.databinding.ListItemDetailCurriculumBinding
import com.wantique.home.domain.model.DetailCurriculum

class DetailCurriculumAdapter : ListAdapter<DetailCurriculum, DetailCurriculumAdapter.DetailCurriculumViewHolder>(object: DiffUtil.ItemCallback<DetailCurriculum>() {
    override fun areItemsTheSame(oldItem: DetailCurriculum, newItem: DetailCurriculum): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DetailCurriculum, newItem: DetailCurriculum): Boolean {
        return oldItem == newItem
    }

}) {
    inner class DetailCurriculumViewHolder(private val binding: ListItemDetailCurriculumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DetailCurriculum) {
            binding.detailCurriculumTvLectureName.text = item.lecture
            binding.detailCurriculumTvStartDate.text = item.start
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailCurriculumViewHolder {
        val binding = ListItemDetailCurriculumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailCurriculumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailCurriculumViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
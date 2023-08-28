package com.wantique.home.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.home.databinding.ListItemCurriculumBinding
import com.wantique.home.domain.model.Curriculum

class CurriculumAdapter : ListAdapter<Curriculum, CurriculumAdapter.CurriculumViewHolder>(object : DiffUtil.ItemCallback<Curriculum>() {
    override fun areItemsTheSame(oldItem: Curriculum, newItem: Curriculum): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Curriculum, newItem: Curriculum): Boolean {
        return oldItem == newItem
    }
}) {
    inner class CurriculumViewHolder(private val binding: ListItemCurriculumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Curriculum) {
            binding.curriculumTvTag.text = item.tag

            val detailCurriculumAdapter = DetailCurriculumAdapter()
            binding.curriculumRvItem.adapter = detailCurriculumAdapter
            detailCurriculumAdapter.submitList(item.detailCurriculum)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurriculumViewHolder {
        val binding = ListItemCurriculumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurriculumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurriculumViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
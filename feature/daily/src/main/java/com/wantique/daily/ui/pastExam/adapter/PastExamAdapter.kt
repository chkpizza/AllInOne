package com.wantique.daily.ui.pastExam.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.daily.databinding.LayoutDescriptionPastExamBinding
import com.wantique.daily.databinding.LayoutNormalPastExamBinding
import com.wantique.daily.domain.model.PastExam

class PastExamAdapter : ListAdapter<PastExam, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<PastExam>() {
    override fun areItemsTheSame(oldItem: PastExam, newItem: PastExam): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PastExam, newItem: PastExam): Boolean {
        return oldItem == newItem
    }
}) {
    inner class NormalPastExamViewHolder(private val binding: LayoutNormalPastExamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PastExam) {
            binding.normalPastExamRvChoice.adapter = ChoiceAdapter(item.answer)
            binding.item = item
        }
    }

    inner class DescriptionPastExamViewHolder(private val binding: LayoutDescriptionPastExamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PastExam) {
            binding.descriptionPastExamRvDescription.adapter = DescriptionAdapter()
            binding.descriptionPastExamRvChoice.adapter = ChoiceAdapter(item.answer)

            binding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when(viewType) {
        NORMAL_VIEW_TYPE -> {
            Log.d("itemViewTypeTest", viewType.toString())
            val binding = LayoutNormalPastExamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            NormalPastExamViewHolder(binding)
        }
        DESCRIPTION_VIEW_TYPE -> {
            Log.d("itemViewTypeTest", viewType.toString())
            val binding = LayoutDescriptionPastExamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DescriptionPastExamViewHolder(binding)
        }
        else -> throw IllegalArgumentException()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItem(position).type) {
            "NORMAL" -> (holder as NormalPastExamViewHolder).bind(getItem(position))
            "DESCRIPTION" -> (holder as DescriptionPastExamViewHolder).bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int = when(getItem(position).type) {
        "NORMAL" -> NORMAL_VIEW_TYPE
        "DESCRIPTION" -> DESCRIPTION_VIEW_TYPE
        else -> throw IllegalArgumentException()
    }

    companion object {
        const val NORMAL_VIEW_TYPE = 1
        const val DESCRIPTION_VIEW_TYPE = 2
    }
}
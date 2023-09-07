package com.wantique.daily.ui.pastExam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.daily.R
import com.wantique.daily.databinding.ListItemChoiceBinding
import com.wantique.daily.domain.model.Choice

class ChoiceAdapter(
    private val answer: Int
) : ListAdapter<Choice, ChoiceAdapter.ChoiceViewHolder>(object : DiffUtil.ItemCallback<Choice>() {
    override fun areItemsTheSame(oldItem: Choice, newItem: Choice): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Choice, newItem: Choice): Boolean {
        return oldItem == newItem
    }

}) {
    private var result = -1
    inner class ChoiceViewHolder(private val binding: ListItemChoiceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Choice) {
            binding.pastExamChoiceTvNumber.text = item.number
            binding.pastExamChoiceTvChoice.text = item.choice

            if(bindingAdapterPosition + 1 == result) {
                binding.pastExamChoiceTvChoice.setTextColor(ContextCompat.getColor(binding.root.context, com.wantique.resource.R.color.colorPrimary))
                binding.pastExamChoiceTvNumber.setTextColor(ContextCompat.getColor(binding.root.context, com.wantique.resource.R.color.colorPrimary))
            }

            binding.root.setOnClickListener {
                if(bindingAdapterPosition +1 == answer) {
                    result = answer
                    notifyDataSetChanged()
                } else {
                    Toast.makeText(binding.root.context, "다시 한번 확인해보세요!", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceViewHolder {
        val binding = ListItemChoiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChoiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChoiceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
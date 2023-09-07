package com.wantique.daily.ui.daily.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.daily.databinding.LayoutDailyLetterBinding
import com.wantique.daily.databinding.LayoutDailyPastExamBinding
import com.wantique.daily.databinding.LayoutDailyRecordBinding
import com.wantique.daily.domain.model.Daily
import com.wantique.daily.ui.daily.adapter.listener.OnPastExamClickListener
import com.wantique.daily.ui.daily.adapter.listener.OnRecordClickListener

class DailyAdapter(
    private val onRecordClickListener: OnRecordClickListener,
    private val onPastExamClickListener: OnPastExamClickListener
) : ListAdapter<Daily, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<Daily>() {
    override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem == newItem
    }

}) {
    private val recordPreviewAdapter = RecordPreviewAdapter(onRecordClickListener)

    inner class DailyLetterViewHolder(private val binding: LayoutDailyLetterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Daily) {
            (item as Daily.DailyLetter).also {
                binding.item = it
            }
        }
    }

    inner class DailyRecordViewHolder(private val binding: LayoutDailyRecordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Daily) {
            (item as Daily.DailyRecord).also {
                binding.dailyRecordRvTodayRecord.adapter = recordPreviewAdapter
                binding.item = it
            }
        }
    }

    inner class DailyPastExamViewHolder(private val binding: LayoutDailyPastExamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Daily) {
            (item as Daily.DailyPastExam).also {
                binding.item = it

                binding.dailyPastExamContainer.setOnClickListener { _ ->
                    onPastExamClickListener.onClick(it.pastExam)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when(viewType) {
        DAILY_LETTER_VIEW_TYPE -> {
            val binding = LayoutDailyLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DailyLetterViewHolder(binding)
        }
        DAILY_RECORD_VIEW_TYPE -> {
            val binding = LayoutDailyRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DailyRecordViewHolder(binding)
        }

        DAILY_PAST_EXAM_VIEW_TYPE -> {
            val binding = LayoutDailyPastExamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DailyPastExamViewHolder(binding)
        }

        else -> throw IllegalArgumentException()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItem(position)) {
            is Daily.DailyLetter -> (holder as DailyLetterViewHolder).bind(getItem(position))
            is Daily.DailyRecord -> (holder as DailyRecordViewHolder).bind(getItem(position))
            is Daily.DailyPastExam -> (holder as DailyPastExamViewHolder).bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int = when(getItem(position)) {
        is Daily.DailyLetter -> DAILY_LETTER_VIEW_TYPE
        is Daily.DailyRecord -> DAILY_RECORD_VIEW_TYPE
        is Daily.DailyPastExam -> DAILY_PAST_EXAM_VIEW_TYPE
    }

    companion object {
        const val DAILY_LETTER_VIEW_TYPE = 1000
        const val DAILY_RECORD_VIEW_TYPE = 2000
        const val DAILY_PAST_EXAM_VIEW_TYPE = 3000
    }
}
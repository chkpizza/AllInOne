package com.wantique.daily.ui.daily.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.daily.databinding.ListItemDailyLetterBinding
import com.wantique.daily.databinding.ListItemDailyPastExamBinding
import com.wantique.daily.databinding.ListItemDailyRecordBinding
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

    inner class DailyLetterViewHolder(private val binding: ListItemDailyLetterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Daily) {
            (item as Daily.DailyLetter).also {
                binding.item = it
            }
        }
    }

    inner class DailyRecordViewHolder(private val binding: ListItemDailyRecordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Daily) {
            (item as Daily.DailyRecord).also {
                binding.dailyRecordRvTodayRecord.adapter = recordPreviewAdapter
                binding.item = it
            }
        }
    }

    inner class DailyPastExamViewHolder(private val binding: ListItemDailyPastExamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Daily) {
            (item as Daily.DailyPastExam).also {
                binding.dailyPastExamTvTitle.text = it.header.title
                binding.dailyPastExamTvSubTitle.text = it.header.subTitle

                binding.dailyPastExamContainer.setOnClickListener { _ ->
                    onPastExamClickListener.onClick(it.pastExam)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when(viewType) {
        DAILY_LETTER_VIEW_TYPE -> {
            val binding = ListItemDailyLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DailyLetterViewHolder(binding)
        }
        DAILY_RECORD_VIEW_TYPE -> {
            val binding = ListItemDailyRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DailyRecordViewHolder(binding)
        }

        DAILY_PAST_EXAM_VIEW_TYPE -> {
            val binding = ListItemDailyPastExamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
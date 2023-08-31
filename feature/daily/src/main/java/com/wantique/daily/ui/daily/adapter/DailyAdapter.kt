package com.wantique.daily.ui.daily.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.daily.databinding.ListItemDailyLetterBinding
import com.wantique.daily.databinding.ListItemDailyPastExamBinding
import com.wantique.daily.databinding.ListItemDailyPromiseBinding
import com.wantique.daily.domain.model.Daily
import com.wantique.daily.ui.daily.adapter.listener.OnPastExamClickListener
import com.wantique.daily.ui.daily.adapter.listener.OnPromisePreviewClickListener
import com.wantique.daily.ui.daily.adapter.listener.OnWritePromiseClickListener

class DailyAdapter(
    private val onWritePromiseClickListener: OnWritePromiseClickListener,
    private val onPromisePreviewClickListener: OnPromisePreviewClickListener,
    private val onPastExamClickListener: OnPastExamClickListener
) : ListAdapter<Daily, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<Daily>() {
    override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem == newItem
    }
}) {
    private val promisePreviewAdapter = PromisePreviewAdapter(onPromisePreviewClickListener)
    private val pastExamPreviewAdapter = PastExamPreviewAdapter(onPastExamClickListener)

    inner class DailyLetterViewHolder(private val binding: ListItemDailyLetterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Daily) {
            (item as Daily.DailyLetter).also {
                binding.item = it
            }
        }
    }

    inner class DailyPromiseViewHolder(private val binding: ListItemDailyPromiseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Daily) {
            (item as Daily.DailyPromise).also {
                binding.item = it
                binding.dailyPromiseRvPromise.adapter = promisePreviewAdapter
                binding.dailyPromiseTvWrite.setOnClickListener {
                    onWritePromiseClickListener.onClick()
                }
            }
        }
    }

    inner class DailyPastExamViewHolder(private val binding: ListItemDailyPastExamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Daily) {
            (item as Daily.DailyPastExam).also {
                binding.dailyPastExamTvTitle.text = it.title
                binding.dailyPastExamTvSubtitle.text = it.subTitle

                binding.dailyPastExamRv.adapter = pastExamPreviewAdapter
                pastExamPreviewAdapter.submitList(it.pastExam)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when(viewType) {
        1000 -> {
            val binding = ListItemDailyLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DailyLetterViewHolder(binding)
        }
        2000 -> {
            val binding = ListItemDailyPromiseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DailyPromiseViewHolder(binding)
        }
        3000 -> {
            val binding = ListItemDailyPastExamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DailyPastExamViewHolder(binding)
        }
        else -> throw IllegalArgumentException()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItem(position)) {
            is Daily.DailyLetter -> (holder as DailyLetterViewHolder).bind(getItem(position))
            is Daily.DailyPromise -> (holder as DailyPromiseViewHolder).bind(getItem(position))
            is Daily.DailyPastExam -> (holder as DailyPastExamViewHolder).bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int) = when(getItem(position)) {
        is Daily.DailyLetter -> 1000
        is Daily.DailyPromise -> 2000
        is Daily.DailyPastExam -> 3000
    }
}
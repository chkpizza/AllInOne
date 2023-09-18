package com.wantique.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.home.databinding.LayoutBannerBinding
import com.wantique.home.databinding.LayoutCategoryBinding
import com.wantique.home.databinding.LayoutExamBinding
import com.wantique.home.databinding.LayoutNoticeBinding
import com.wantique.home.databinding.LayoutProfessorBinding
import com.wantique.home.domain.model.Home
import com.wantique.home.ui.home.adapter.listener.OnAllNoticeClickListener
import com.wantique.home.ui.home.adapter.listener.OnCategoryClickListener
import com.wantique.home.ui.home.adapter.listener.OnNoticeClickListener
import com.wantique.home.ui.home.adapter.listener.OnProfessorClickListener

class HomeAdapter(
    private val onCategoryClickListener: OnCategoryClickListener,
    private val onProfessorClickListener: OnProfessorClickListener,
    private val onNoticeClickListener: OnNoticeClickListener,
    private val onAllNoticeClickListener: OnAllNoticeClickListener
) : ListAdapter<Home, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<Home>() {
    override fun areItemsTheSame(oldItem: Home, newItem: Home): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Home, newItem: Home): Boolean {
        return oldItem == newItem
    }
}) {
    private val bannerAdapter = BannerAdapter()
    private val categoryAdapter = CategoryAdapter(onCategoryClickListener)
    private val professorAdapter = ProfessorAdapter(onProfessorClickListener)
    private val examAdapter = ExamAdapter()
    private val noticeAdapter = NoticeAdapter(onNoticeClickListener)

    inner class BannerWrapperViewHolder(private val binding: LayoutBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Home) {
            (item as Home.Banner).also {
                binding.homeVpBanner.adapter = bannerAdapter
                binding.homeTvNotice.isSelected = true
                binding.homeTvNotice.setHorizontallyScrolling(true)
                binding.item = it
            }
        }
    }

    inner class CategoryWrapperViewHolder(private val binding: LayoutCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Home) {
            (item as Home.Category).also {
                binding.homeTvCategoryTitle.text = it.title
                binding.homeRvCategory.adapter = categoryAdapter
                categoryAdapter.submitList(it.categories)
            }
        }
    }

    inner class ProfessorWrapperViewHolder(private val binding: LayoutProfessorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Home) {
            (item as Home.Professor).also {
                binding.homeRvProfessor.adapter = professorAdapter
                professorAdapter.submitList(it.professors)
            }
        }
    }

    inner class ExamWrapperViewHolder(private val binding: LayoutExamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Home) {
            (item as Home.YearlyExamPlan).also {
                binding.homeTvExamTitle.text = it.title
                binding.homeRvExam.adapter = examAdapter
                examAdapter.submitList(it.exam)
            }
        }
    }

    inner class NoticeWrapperViewHolder(private val binding: LayoutNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Home) {
            (item as Home.Notice).also {
                binding.noticeTvHeader.text = it.header
                binding.noticeRv.adapter = noticeAdapter
                noticeAdapter.submitList(it.notice)

                binding.noticeTvMoreView.setOnClickListener {
                    onAllNoticeClickListener.onClick()
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when(getItem(position)) {
        is Home.Banner -> 1000
        is Home.Category -> 2000
        is Home.Professor -> 3000
        is Home.YearlyExamPlan -> 4000
        is Home.Notice -> 5000
        else -> throw RuntimeException()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when(viewType) {
        1000 -> {
            val binding = LayoutBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            BannerWrapperViewHolder(binding)
        }
        2000 -> {
            val binding = LayoutCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            CategoryWrapperViewHolder(binding)
        }
        3000 -> {
            val binding = LayoutProfessorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ProfessorWrapperViewHolder(binding)
        }
        4000 -> {
            val binding = LayoutExamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ExamWrapperViewHolder(binding)
        }

        5000 -> {
            val binding = LayoutNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            NoticeWrapperViewHolder(binding)
        }
        else -> throw RuntimeException()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItem(position)) {
            is Home.Banner -> (holder as BannerWrapperViewHolder).bind(getItem(position))
            is Home.Category -> (holder as CategoryWrapperViewHolder).bind(getItem(position))
            is Home.Professor -> (holder as ProfessorWrapperViewHolder).bind(getItem(position))
            is Home.YearlyExamPlan -> (holder as ExamWrapperViewHolder).bind(getItem(position))
            is Home.Notice -> (holder as NoticeWrapperViewHolder).bind(getItem(position))
        }
    }

    fun getCategoryAdapter() = categoryAdapter
}
package com.wantique.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.home.databinding.ListItemBannerWrapperBinding
import com.wantique.home.databinding.ListItemCategoryWrapperBinding
import com.wantique.home.databinding.ListItemExamWrapperBinding
import com.wantique.home.databinding.ListItemProfessorWrapperBinding
import com.wantique.home.domain.model.Home
import com.wantique.home.ui.home.adapter.listener.OnCategoryClickListener
import com.wantique.home.ui.home.adapter.listener.OnProfessorClickListener

class HomeAdapter(
    private val onCategoryClickListener: OnCategoryClickListener,
    private val onProfessorClickListener: OnProfessorClickListener
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

    inner class BannerWrapperViewHolder(private val binding: ListItemBannerWrapperBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Home) {
            (item as Home.Banner).also {
                binding.homeVpBanner.adapter = bannerAdapter
                binding.homeTvNotice.isSelected = true
                binding.homeTvNotice.setHorizontallyScrolling(true)
                binding.item = it
            }
        }
    }

    inner class CategoryWrapperViewHolder(private val binding: ListItemCategoryWrapperBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Home) {
            (item as Home.Category).also {
                binding.homeTvCategoryTitle.text = it.title
                binding.homeRvCategory.adapter = categoryAdapter
                categoryAdapter.submitList(it.categories)
            }
        }
    }

    inner class ProfessorWrapperViewHolder(private val binding: ListItemProfessorWrapperBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Home) {
            (item as Home.Professor).also {
                binding.homeRvProfessor.adapter = professorAdapter
                professorAdapter.submitList(it.professors)
            }
        }
    }

    inner class ExamWrapperViewHolder(private val binding: ListItemExamWrapperBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Home) {
            (item as Home.Exam).also {
                binding.homeTvExamTitle.text = it.title
                binding.homeRvExam.adapter = examAdapter
                examAdapter.submitList(it.exam)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when(getItem(position)) {
        is Home.Banner -> 1000
        is Home.Category -> 2000
        is Home.Professor -> 3000
        is Home.Exam -> 4000
        else -> throw RuntimeException()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when(viewType) {
        1000 -> {
            val binding = ListItemBannerWrapperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            BannerWrapperViewHolder(binding)
        }
        2000 -> {
            val binding = ListItemCategoryWrapperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            CategoryWrapperViewHolder(binding)
        }
        3000 -> {
            val binding = ListItemProfessorWrapperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ProfessorWrapperViewHolder(binding)
        }
        4000 -> {
            val binding = ListItemExamWrapperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ExamWrapperViewHolder(binding)
        }
        else -> throw RuntimeException()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItem(position)) {
            is Home.Banner -> (holder as BannerWrapperViewHolder).bind(getItem(position))
            is Home.Category -> (holder as CategoryWrapperViewHolder).bind(getItem(position))
            is Home.Professor -> (holder as ProfessorWrapperViewHolder).bind(getItem(position))
            is Home.Exam -> (holder as ExamWrapperViewHolder).bind(getItem(position))
        }
    }

    fun getCategoryAdapter() = categoryAdapter
}
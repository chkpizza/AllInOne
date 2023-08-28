package com.wantique.home.ui.details.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.base.state.UiState
import com.wantique.base.state.isSuccessOrNull
import com.wantique.home.domain.model.ProfessorInfo
import com.wantique.home.domain.model.YearlyCurriculum

object DetailsBindingAdapters {
    @BindingAdapter("slogan")
    @JvmStatic
    fun setSlogan(view: TextView, item: UiState<ProfessorInfo>) {
        item.isSuccessOrNull()?.let {
            view.text = it.slogan.replace("\\n", "\n")
        }
    }

    @BindingAdapter("curriculum")
    @JvmStatic
    fun setCurriculum(view: RecyclerView, item: UiState<YearlyCurriculum>) {
        item.isSuccessOrNull()?.let {
            (view.adapter as CurriculumAdapter).submitList(it.curriculum)
        }
    }

    @BindingAdapter("toolbar")
    @JvmStatic
    fun setToolbar(view: TextView, item: UiState<ProfessorInfo>) {
        item.isSuccessOrNull()?.let {
            view.text = "${it.name} 교수님"
        }
    }

    @BindingAdapter("curriculumYear")
    @JvmStatic
    fun setCurriculumYear(view: TextView, item: UiState<YearlyCurriculum>) {
        item.isSuccessOrNull()?.let {
            view.text = "${it.year}년도 커리큘럼 가이드"
        }
    }
}
package com.wantique.daily.ui.daily.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.base.state.UiState
import com.wantique.base.state.isSuccessOrNull
import com.wantique.daily.domain.model.Daily
import com.wantique.daily.domain.model.Record

object DailyBindingAdapter {
    @BindingAdapter("daily")
    @JvmStatic
    fun setDaily(view: RecyclerView, item: UiState<List<Daily>>) {
        item.isSuccessOrNull()?.let {
            (view.adapter as DailyAdapter).submitList(it)
        }
    }

    @BindingAdapter("letter")
    @JvmStatic
    fun setLetter(view: TextView, item: String) {
        view.text = item.replace("\\n", "\n")
    }

    @BindingAdapter("preview")
    @JvmStatic
    fun setPreview(view: RecyclerView, item: List<Record>) {
        (view.adapter as RecordPreviewAdapter).submitList(item)
    }
}
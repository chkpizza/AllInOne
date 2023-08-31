package com.wantique.daily.ui.daily.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wantique.base.state.UiState
import com.wantique.base.state.getValue
import com.wantique.base.state.isSuccessOrNull
import com.wantique.daily.domain.model.Daily
import com.wantique.daily.domain.model.Promise

object DailyBindingAdapters {
    @BindingAdapter("daily")
    @JvmStatic
    fun setDaily(view: RecyclerView, item: UiState<List<Daily>>) {
        item.isSuccessOrNull()?.let {
            (view.adapter as DailyAdapter).submitList(item.getValue())
        }
    }

    @BindingAdapter("letter")
    @JvmStatic
    fun setLetter(view: TextView, letter: String) {
        view.text = letter.replace("\\n","\n")
    }

    @BindingAdapter("promisePreview")
    @JvmStatic
    fun setPromisePreview(view: RecyclerView, promise: List<Promise>) {
        if(promise.isNotEmpty()) {
            (view.adapter as PromisePreviewAdapter).submitList(promise)
        }
    }

    @BindingAdapter("image")
    @JvmStatic
    fun setImage(view: ImageView, imageUrl: String) {
        Glide.with(view.context)
            .load(imageUrl)
            .into(view)
    }
}
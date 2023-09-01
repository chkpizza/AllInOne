package com.wantique.daily.ui.record.adapter

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.wantique.base.state.UiState
import com.wantique.base.state.isSuccessOrNull

object RecordBindingAdapters {
    @BindingAdapter("image")
    @JvmStatic
    fun setImage(view: ImageView, item: UiState<Uri>) {
        item.isSuccessOrNull()?.let {
            Glide.with(view.context)
                .load(it)
                .into(view)
        }
    }
}
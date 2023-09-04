package com.wantique.daily.ui.record.adapter

import android.net.Uri
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.wantique.base.state.UiState
import com.wantique.base.state.isSuccessOrNull
import com.wantique.daily.R
import com.wantique.daily.domain.model.Record
import com.wantique.firebase.Firebase

object RecordBindingAdapters {
    @BindingAdapter("uploadImage")
    @JvmStatic
    fun setImage(view: ImageView, item: UiState<Uri>) {
        item.isSuccessOrNull()?.let {
            Glide.with(view.context)
                .load(it)
                .into(view)
        }
    }

    @BindingAdapter("recordBackgroundImage")
    @JvmStatic
    fun setRecordBackgroundImage(view: ImageView, item: String) {
        Glide.with(view.context)
            .load(item).into(view)
    }

    @BindingAdapter("profileImage")
    @JvmStatic
    fun setProfileImage(view: ImageView, item: String) {
        Glide.with(view.context)
            .load(item)
            .placeholder(R.drawable.ic_empty_profile)
            .into(view)
    }

    @BindingAdapter("report")
    @JvmStatic
    fun setReport(view: ImageView, item: Record) {
        view.isVisible = Firebase.getInstance().getCurrentUserUid() != item.authorUid
    }
}
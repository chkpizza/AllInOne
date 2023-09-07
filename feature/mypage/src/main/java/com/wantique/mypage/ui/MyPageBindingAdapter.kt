package com.wantique.mypage.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.wantique.base.state.UiState
import com.wantique.base.state.isSuccessOrNull
import com.wantique.mypage.domain.model.UserProfile

object MyPageBindingAdapter {
    @BindingAdapter("profileImage")
    @JvmStatic
    fun setProfileImage(view: ImageView, item: UiState<UserProfile>) {
        item.isSuccessOrNull()?.let {
            Glide.with(view.context)
                .load(it.profileImageUrl)
                .into(view)
        }
    }

    @BindingAdapter("nickName")
    @JvmStatic
    fun setNickName(view: TextView, item: UiState<UserProfile>) {
        item.isSuccessOrNull()?.let {
            view.text = it.name
        }
    }
}
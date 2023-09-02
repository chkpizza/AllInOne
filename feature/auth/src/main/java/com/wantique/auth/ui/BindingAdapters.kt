package com.wantique.auth.ui

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.wantique.auth.domain.model.Cover
import com.wantique.base.state.UiState
import com.wantique.base.state.getValue
import com.wantique.base.state.isSuccessOrNull
import java.text.DecimalFormat

object BindingAdapters {
    @BindingAdapter("timer")
    @JvmStatic
    fun setTimer(view: TextView, item: UiState<Int>) {
        item.isSuccessOrNull()?.let {
            if(it == 0) {
                view.text = "인증번호 받기"
            } else {
                val minute = it / 60
                val second = it % 60

                view.text = "인증번호 다시 받기 (${DecimalFormat("00").format(minute)}:${DecimalFormat("00").format(second)})"
            }
        }
    }

    @BindingAdapter("error")
    @JvmStatic
    fun setError(view: ConstraintLayout, error: Throwable?) {
        error?.let {
            Toast.makeText(view.context, "에러 발생!!", Toast.LENGTH_SHORT).show()
        }
    }

    @BindingAdapter("image")
    @JvmStatic
    fun setImage(view: ImageView, item: UiState<String>) {
        item.isSuccessOrNull()?.let {
            Glide.with(view.context)
                .load(it)
                .into(view)
        }
    }

    @BindingAdapter("cover")
    @JvmStatic
    fun setCover(view: ImageView, item: UiState<Cover>) {
        item.isSuccessOrNull()?.let {
            Glide.with(view.context)
                .load(item.getValue().imageUrl)
                .into(view)
        }
    }
}
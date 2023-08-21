package com.wantique.auth.ui

import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import java.text.DecimalFormat

object BindingAdapters {
    @BindingAdapter("timer")
    @JvmStatic
    fun setTimer(view: TextView, time: Int) {
        if(time == 0) {
            view.text = "인증번호 받기"
        } else {
            val minute = time / 60
            val second = time % 60

            view.text = "인증번호 다시 받기 (${DecimalFormat("00").format(minute)}:${DecimalFormat("00").format(second)})"
        }
    }

    @BindingAdapter("error")
    @JvmStatic
    fun setError(view: ConstraintLayout, error: Throwable?) {
        error?.let {
            Toast.makeText(view.context, "에러 발생!!", Toast.LENGTH_SHORT).show()
        }
    }
}
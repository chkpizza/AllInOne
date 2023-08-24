package com.wantique.base.ui

import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

object BaseBindingAdapter {
    @BindingAdapter("errorHandler")
    @JvmStatic
    fun handleError(view: ConstraintLayout, e: Throwable?) {
        e?.let {
            if(it.message == "NETWORK_CONNECTION_ERROR") {
                view.isVisible = true
            } else {
                view.isVisible = false
                Toast.makeText(view.context, it.message, Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            view.isVisible = false
        }
    }
}
package com.wantique.base.ui

import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.wantique.base.state.UiState

object BaseBindingAdapter {
    @BindingAdapter("errorHandler")
    @JvmStatic
    fun handleError(view: ConstraintLayout, e: Throwable?) {
        e?.let {
            if(it.message == "NETWORK_CONNECTION_ERROR") {
                Snackbar.make(view, "네트워크 연결이 불안정합니다\n네트워크 연결을 확인해주세요!", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(view, it.message.toString(), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    @BindingAdapter("isRefreshing")
    @JvmStatic
    fun setRefreshing(view: SwipeRefreshLayout, state: Boolean) {
        Log.d("loadingState", state.toString())
        if(!state) {
            view.isRefreshing = false
        }
    }
}
package com.wantique.home.ui.notice.adapter

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wantique.base.state.UiState
import com.wantique.base.state.isSuccessOrNull
import com.wantique.home.domain.model.NoticeItem

object NoticeBindingAdapter {
    @BindingAdapter("noticeTitle")
    @JvmStatic
    fun setNoticeTitle(view: TextView, item: UiState<NoticeItem>) {
        item.isSuccessOrNull()?.let {
            view.text = it.title
        }
    }

    @BindingAdapter("noticeBody")
    @JvmStatic
    fun setNoticeBody(view: TextView, item: UiState<NoticeItem>) {
        item.isSuccessOrNull()?.let {
            view.text = it.body
        }
    }

    @BindingAdapter("noticeToolbarTitle")
    @JvmStatic
    fun setNoticeToolbarTitle(view: TextView, item: UiState<NoticeItem>) {
        item.isSuccessOrNull()?.let {
            view.text = "${it.name} 소식"
        }
    }

    @BindingAdapter("noticeLink")
    @JvmStatic
    fun setNoticeLink(view: TextView, item: UiState<NoticeItem>) {
        item.isSuccessOrNull()?.let {
            view.tag = it.url
        }
    }

    @BindingAdapter("notices")
    @JvmStatic
    fun setNotices(view: RecyclerView, item: UiState<List<NoticeItem>>) {
        item.isSuccessOrNull()?.let {
            (view.adapter as? NoticeListAdapter)?.let { adapter ->
                adapter.submitList(it)
            }
        }
    }
}
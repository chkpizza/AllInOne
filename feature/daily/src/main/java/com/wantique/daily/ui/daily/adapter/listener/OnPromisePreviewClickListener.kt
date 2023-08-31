package com.wantique.daily.ui.daily.adapter.listener

import com.wantique.daily.domain.model.Promise

interface OnPromisePreviewClickListener {
    fun onClick(position: Int, promise: List<Promise>)
}
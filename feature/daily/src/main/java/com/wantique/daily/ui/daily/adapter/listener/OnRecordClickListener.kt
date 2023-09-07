package com.wantique.daily.ui.daily.adapter.listener

import com.wantique.daily.domain.model.Record

interface OnRecordClickListener {
    fun onClick(position: Int, record: List<Record>)
}
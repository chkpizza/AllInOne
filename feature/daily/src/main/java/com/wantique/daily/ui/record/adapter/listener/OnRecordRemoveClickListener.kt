package com.wantique.daily.ui.record.adapter.listener

import com.wantique.daily.domain.model.Record

interface OnRecordRemoveClickListener {
    fun onClick(record: Record)
}
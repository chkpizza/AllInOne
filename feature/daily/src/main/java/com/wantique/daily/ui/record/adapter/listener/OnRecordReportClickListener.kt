package com.wantique.daily.ui.record.adapter.listener

import com.wantique.daily.domain.model.Record

interface OnRecordReportClickListener {
    fun onClick(record: Record)
}
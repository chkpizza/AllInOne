package com.wantique.daily.ui.daily.adapter.listener

import com.wantique.daily.domain.model.TodayPastExam

interface OnPastExamClickListener {
    fun onClick(pastExam: TodayPastExam)
}
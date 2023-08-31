package com.wantique.daily.ui.daily.adapter.listener

import com.wantique.daily.domain.model.PastExam

interface OnPastExamClickListener {
    fun onClick(position: Int, pastExam: List<PastExam>)
}
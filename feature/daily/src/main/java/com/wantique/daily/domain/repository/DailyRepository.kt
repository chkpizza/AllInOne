package com.wantique.daily.domain.repository

import com.wantique.base.state.Resource
import com.wantique.daily.domain.model.Daily
import com.wantique.firebase.model.DailyLetterDto
import com.wantique.firebase.model.DailyPastExamDto
import com.wantique.firebase.model.DailyRecordDto
import kotlinx.coroutines.flow.Flow

interface DailyRepository {
    fun getDailyLetter(): Flow<Resource<DailyLetterDto>>
    fun getDailyRecord(): Flow<Resource<DailyRecordDto>>
    fun getDailyPastExam(): Flow<Resource<DailyPastExamDto>>
}
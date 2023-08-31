package com.wantique.daily.domain.repository

import com.wantique.base.state.Resource
import com.wantique.daily.domain.model.Daily
import kotlinx.coroutines.flow.Flow

interface DailyRepository {
    fun getDailyLetter(): Flow<Resource<Daily.DailyLetter>>
    fun getDailyPromise(): Flow<Resource<Daily.DailyPromise>>
}
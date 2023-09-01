package com.wantique.daily.domain.repository

import com.wantique.base.state.Resource
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    fun registerRecord(imageUri: String, body: String): Flow<Resource<Boolean>>
}
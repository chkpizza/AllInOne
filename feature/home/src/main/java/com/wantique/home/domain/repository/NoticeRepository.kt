package com.wantique.home.domain.repository

import com.wantique.base.state.Resource
import com.wantique.home.domain.model.NoticeItem
import kotlinx.coroutines.flow.Flow

interface NoticeRepository {
    fun getNotice(id: String): Flow<Resource<NoticeItem>>
    fun getAllNotice(): Flow<Resource<List<NoticeItem>>>
}
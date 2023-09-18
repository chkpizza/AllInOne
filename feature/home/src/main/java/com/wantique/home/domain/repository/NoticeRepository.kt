package com.wantique.home.domain.repository

import com.wantique.base.state.Resource
import com.wantique.firebase.model.NoticeItemDto
import com.wantique.home.domain.model.NoticeItem
import kotlinx.coroutines.flow.Flow

interface NoticeRepository {
    fun getNotice(id: String): Flow<Resource<NoticeItemDto>>
    fun getAllNotice(): Flow<Resource<List<NoticeItemDto>>>
}
package com.wantique.mypage.domain.repository

import com.wantique.base.state.Resource
import kotlinx.coroutines.flow.Flow

interface RecommendRepository {
    fun registerRecommend(recommend: String): Flow<Resource<Boolean>>
}
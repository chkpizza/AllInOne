package com.wantique.mypage.domain.repository

import com.wantique.base.state.Resource
import com.wantique.mypage.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface MyPageRepository {
    fun getUserProfile(): Flow<Resource<UserProfile>>
}
package com.wantique.mypage.domain.repository

import com.wantique.base.state.Resource
import kotlinx.coroutines.flow.Flow

interface EditRepository {
    fun isDuplicateNickName(nickName: String): Flow<Resource<Boolean>>
    fun modifyUserProfile(imageUri: String, nickName: String): Flow<Resource<Boolean>>
    fun withdrawal(): Flow<Resource<Boolean>>
}
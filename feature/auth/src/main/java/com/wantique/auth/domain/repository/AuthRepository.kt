package com.wantique.auth.domain.repository

import com.wantique.auth.domain.model.Cover
import com.wantique.base.state.Resource
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    fun getCoverImage(): Flow<Resource<Cover>>
    fun isExistUser(): Flow<Resource<Boolean>>
    fun isWithdrawalUser(): Flow<Resource<Boolean>>
    fun registerUser(imageUri: String = "", nickName: String = ""): Flow<Resource<Boolean>>
    fun redoUser(): Flow<Resource<Boolean>>
    fun isDuplicateNickName(nickName: String): Flow<Resource<Boolean>>
}
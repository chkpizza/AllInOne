package com.wantique.auth.domain.repository

import com.wantique.base.state.Resource
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    fun isExistUser(): Flow<Resource<Boolean>>
    fun isWithdrawalUser(): Flow<Resource<Boolean>>
    fun registerUser(imageUri: String = "", nickName: String = ""): Flow<Resource<Boolean>>
    fun redoUser(): Flow<Resource<Boolean>>
}
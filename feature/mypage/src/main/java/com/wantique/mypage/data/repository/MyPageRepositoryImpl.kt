package com.wantique.mypage.data.repository

import com.wantique.base.state.Resource
import com.wantique.firebase.Firebase
import com.wantique.firebase.model.UserDto
import com.wantique.mypage.domain.repository.MyPageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(private val dispatcher: CoroutineDispatcher, private val firebase: Firebase) : MyPageRepository {
    override fun getUserProfile(): Flow<Resource<UserDto>> = flow {
        firebase.getCurrentUserProfile()?.let {
            emit(Resource.Success(it))
        } ?: run {
            emit(Resource.Error(Throwable("데이터를 가져오지 못했습니다")))
        }
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)
}
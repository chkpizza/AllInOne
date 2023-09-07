package com.wantique.mypage.data.repository

import com.wantique.base.state.Resource
import com.wantique.firebase.Firebase
import com.wantique.mypage.domain.repository.EditRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EditRepositoryImpl @Inject constructor(private val dispatcher: CoroutineDispatcher, private val firebase: Firebase) : EditRepository {
    override fun isDuplicateNickName(nickName: String) = flow<Resource<Boolean>> {
        emit(Resource.Success(firebase.isDuplicateNickName(nickName)))
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)

    override fun modifyUserProfile(imageUri: String, nickName: String) = flow<Resource<Boolean>> {
        emit(Resource.Success(firebase.modifyUserProfile(imageUri, nickName)))
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)

    override fun withdrawal() = flow<Resource<Boolean>> {
        emit(Resource.Success(firebase.withdrawal()))
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)
}
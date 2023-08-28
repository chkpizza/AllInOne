package com.wantique.auth.data.repository

import com.wantique.auth.domain.repository.AuthRepository
import com.wantique.base.state.Resource
import com.wantique.firebase.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val dispatcher: CoroutineDispatcher, private val firestore: Firebase) : AuthRepository {
    override fun isExistUser() = flow<Resource<Boolean>> {
        emit(Resource.Success(firestore.getCurrentUser()))
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)

    override fun isWithdrawalUser()= flow<Resource<Boolean>> {
        emit(Resource.Success(firestore.checkWithdrawalUser()))
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)

    override fun registerUser(imageUri: String, nickName: String): Flow<Resource<Boolean>> = flow<Resource<Boolean>> {
        emit(Resource.Success(firestore.registerUser(imageUri, nickName)))
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)

    override fun redoUser() = flow<Resource<Boolean>> {
        emit(Resource.Success(firestore.redoUser()))
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)

    override fun isDuplicateNickName(nickName: String) = flow<Resource<Boolean>> {
        emit(Resource.Success(firestore.isDuplicateNickName(nickName)))
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)
}
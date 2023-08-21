package com.wantique.auth.data.repository

import com.wantique.auth.domain.repository.AuthRepository
import com.wantique.base.state.Resource
import com.wantique.firebase.FireStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val dispatcher: CoroutineDispatcher, private val firestore: FireStore) : AuthRepository {
    override fun isExistUser() = flow<Resource<Boolean>> {
        emit(Resource.Success(firestore.getCurrentUser()))
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)

}
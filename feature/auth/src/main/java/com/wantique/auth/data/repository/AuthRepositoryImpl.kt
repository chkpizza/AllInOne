package com.wantique.auth.data.repository

import com.wantique.auth.data.mapper.Mapper
import com.wantique.auth.domain.model.Cover
import com.wantique.auth.domain.repository.AuthRepository
import com.wantique.base.state.Resource
import com.wantique.firebase.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val dispatcher: CoroutineDispatcher, private val firebase: Firebase) : AuthRepository {
    override fun getCoverImage(): Flow<Resource<Cover>> = flow {
        firebase.getCoverImage()?.let {
            emit(Resource.Success(Mapper.mapperToDomain(it)))
        } ?: run {
            emit(Resource.Error(Throwable("앱 커버 이미지를 가져오지 못했습니다")))
        }
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)

    override fun isExistUser() = flow<Resource<Boolean>> {
        emit(Resource.Success(firebase.isExistUser()))
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)

    override fun isWithdrawalUser()= flow<Resource<Boolean>> {
        emit(Resource.Success(firebase.checkWithdrawalUser()))
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)

    override fun registerUser(imageUri: String, nickName: String): Flow<Resource<Boolean>> = flow<Resource<Boolean>> {
        emit(Resource.Success(firebase.registerUser(imageUri, nickName)))
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)

    override fun redoUser() = flow<Resource<Boolean>> {
        emit(Resource.Success(firebase.redoUser()))
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)

    override fun isDuplicateNickName(nickName: String) = flow<Resource<Boolean>> {
        emit(Resource.Success(firebase.isDuplicateNickName(nickName)))
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)
}
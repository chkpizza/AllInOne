package com.wantique.daily.data.repository

import com.wantique.base.state.Resource
import com.wantique.daily.domain.repository.RecordRepository
import com.wantique.firebase.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val firebase: Firebase
) : RecordRepository {
    override fun registerRecord(imageUri: String, body: String): Flow<Resource<Boolean>> = flow {
        if(firebase.registerRecord(imageUri, body)) {
            emit(Resource.Success(true))
        } else {
            emit(Resource.Error(Throwable("등록에 실패하였습니다")))
        }
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)

}
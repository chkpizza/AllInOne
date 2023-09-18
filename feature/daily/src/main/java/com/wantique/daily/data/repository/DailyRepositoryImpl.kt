package com.wantique.daily.data.repository

import com.wantique.base.state.Resource
import com.wantique.daily.domain.repository.DailyRepository
import com.wantique.firebase.Firebase
import com.wantique.firebase.model.DailyLetterDto
import com.wantique.firebase.model.DailyPastExamDto
import com.wantique.firebase.model.DailyRecordDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DailyRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val firebase: Firebase
) : DailyRepository {
    override fun getDailyLetter(): Flow<Resource<DailyLetterDto>> = flow {
        firebase.getDailyLetter()?.let {
            emit(Resource.Success(it))
        } ?: run {
            emit(Resource.Error(Throwable("데이터를 불러오지 못했습니다")))
        }
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)

    override fun getDailyRecord(): Flow<Resource<DailyRecordDto>> = flow {
        firebase.getDailyRecord()?.let {
            emit(Resource.Success(it))
        } ?: run {
            emit(Resource.Error(Throwable("데이터를 불러오지 못했습니다")))
        }
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)

    override fun getDailyPastExam(): Flow<Resource<DailyPastExamDto>> = flow {
        firebase.getDailyPastExam()?.let {
            emit(Resource.Success(it))
        } ?: run {
            emit(Resource.Error(Throwable("데이터를 불러오지 못했습니다")))
        }
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)
}
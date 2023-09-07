package com.wantique.daily.data.repository

import com.wantique.base.state.Resource
import com.wantique.daily.data.mapper.Mapper
import com.wantique.daily.domain.model.Daily
import com.wantique.daily.domain.repository.DailyRepository
import com.wantique.firebase.Firebase
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
    override fun getDailyLetter(): Flow<Resource<Daily.DailyLetter>> = flow {
        firebase.getDailyLetter()?.let {
            emit(Resource.Success(Mapper.mapperToDomain(it)))
        } ?: run {
            emit(Resource.Error(Throwable("데이터를 불러오지 못했습니다")))
        }
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)

    override fun getDailyRecord(): Flow<Resource<Daily.DailyRecord>> = flow {
        firebase.getDailyRecord()?.let {
            emit(Resource.Success(Mapper.mapperToDomain(it)))
        } ?: run {
            emit(Resource.Error(Throwable("데이터를 불러오지 못했습니다")))
        }
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)


    override fun getDailyPastExam(): Flow<Resource<Daily.DailyPastExam>> = flow {
        firebase.getDailyPastExam()?.let {
            emit(Resource.Success(Mapper.mapperToDomain(it)))
        } ?: run {
            emit(Resource.Error(Throwable("기출문제 데이터를 불러오지 못했습니다")))
        }
    }.catch { e ->
        emit(Resource.Error(e))
    }.flowOn(dispatcher)
}
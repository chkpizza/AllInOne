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

class DailyRepositoryImpl @Inject constructor(private val dispatcher: CoroutineDispatcher, private val firebase: Firebase) : DailyRepository {
    override fun getDailyLetter(): Flow<Resource<Daily.DailyLetter>> = flow {
        firebase.getDailyLetter()?.let {
            emit(Resource.Success(Mapper.mapperToDomain(it)))
        } ?: run {
            emit(Resource.Error(Throwable("Letter 를 가져오지 못했습니다")))
        }
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)

    override fun getDailyPromise(): Flow<Resource<Daily.DailyPromise>> = flow {
        firebase.getDailyPromiseTitle()?.let {
            emit(Resource.Success(Mapper.mapperToDomain(it, firebase.getDailyPromise())))
        } ?: run {
            emit(Resource.Error(Throwable("오늘의 다짐 정보를 가져오지 못했습니다")))
        }
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)
}
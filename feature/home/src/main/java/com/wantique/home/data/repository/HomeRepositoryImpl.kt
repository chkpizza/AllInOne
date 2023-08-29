package com.wantique.home.data.repository

import com.wantique.base.state.Resource
import com.wantique.firebase.Firebase
import com.wantique.home.data.mapper.Mapper
import com.wantique.home.domain.model.Home
import com.wantique.home.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val dispatcher: CoroutineDispatcher, private val firebase: Firebase) : HomeRepository {
    override fun getBanner(): Flow<Resource<Home.Banner>> = flow {
        firebase.getBanner()?.let {
            emit(Resource.Success(Mapper.mapperToDomain(it)))
        } ?: run {
            emit(Resource.Error(Throwable("배너를 가져오지 못했습니다")))
        }
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)

    override fun getCategory(): Flow<Resource<Home.Category>> = flow {
        firebase.getCategory()?.let {
            emit(Resource.Success(Mapper.mapperToDomain(it)))
        } ?: run {
            emit(Resource.Error(Throwable("카테고리 정보를 가져오지 못했습니다")))
        }
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)

    override fun getProfessors(): Flow<Resource<List<Home.Professor>>> = flow<Resource<List<Home.Professor>>> {
        emit(Resource.Success(Mapper.mapperToDomain(firebase.getProfessors())))
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)

    override fun getYearlyExam(): Flow<Resource<Home.Exam>> = flow {
        firebase.getYearlyExam()?.let {
            emit(Resource.Success(Mapper.mapperToDomain(it)))
        } ?: run {
            emit(Resource.Error(Throwable("올해 시험 정보를 가져오지 못했습니다")))
        }
    }.catch {
        emit(Resource.Error(it))
    }.flowOn(dispatcher)
}